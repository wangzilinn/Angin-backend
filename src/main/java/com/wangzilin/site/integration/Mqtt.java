package com.***REMOVED***.site.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.chat.ChatMessage;
import com.***REMOVED***.site.services.impl.ChatService;
import com.***REMOVED***.site.util.SslUtil;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.net.ssl.SSLSocketFactory;

@Configuration
public class Mqtt {

    final private ChatService chatService;

    final private ObjectMapper mapper;

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.clientId}")
    private String clientId;

    MqttPahoMessageDrivenChannelAdapter adapter;

    final private static Logger log = LoggerFactory.getLogger(Mqtt.class);

    public Mqtt(ChatService chatService, ObjectMapper mapper) {
        this.chatService = chatService;
        this.mapper = mapper;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() throws Exception {
        SSLSocketFactory socketFactory = SslUtil.getSocketFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setSocketFactory(socketFactory);
        DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
        defaultMqttPahoClientFactory.setConnectionOptions(options);

        adapter =
                new MqttPahoMessageDrivenChannelAdapter(url, clientId,
                        defaultMqttPahoClientFactory,
                        "addChannel");
        adapter.setCompletionTimeout(30000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            assert topic != null;
            String payLoadJson = (String) message.getPayload();
            try {
                //用户新增了一个聊天channel, 服务器应立刻订阅
                if (topic.equals("addChannel")) {
                    String newChannelName = mapper.readTree(payLoadJson).path("channelName").asText();
                    adapter.addTopic(newChannelName, 2);
                    log.info("add topic: " + newChannelName);
                }
                //处理所有的用户channel
                else if (topic.startsWith("user-")) {
                    //获得channel name: channel-前缀后面的内容就是channel的名字
                    String channelName = topic.substring(8);
                    ChatMessage userMessage = mapper.readValue(payLoadJson, ChatMessage.class);
                    chatService.saveMessage(channelName, userMessage);
                    log.info(channelName + " " + payLoadJson);
                }
            } catch (JsonProcessingException e) {
                log.info(topic + " " + payLoadJson);
                e.printStackTrace();
            }
        };
    }
}

