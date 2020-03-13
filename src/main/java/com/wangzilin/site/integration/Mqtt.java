package com.***REMOVED***.site.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.MessageModel;
import com.***REMOVED***.site.services.MessageService;
import com.***REMOVED***.site.util.SslUtil;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MessageService messageService;

    @Autowired
    ObjectMapper mapper;

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

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("ssl://***REMOVED***:8883", "backend",
                        defaultMqttPahoClientFactory,
                        "chat");
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
            String json = (String) message.getPayload();
            try {
                MessageModel messageModel = mapper.readValue(json, MessageModel.class);
                messageService.saveMessage(messageModel);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(message.getPayload());
        };
    }
}

