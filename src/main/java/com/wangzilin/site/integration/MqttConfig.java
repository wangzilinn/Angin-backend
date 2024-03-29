// package com.wangzilin.site.integration;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.wangzilin.site.model.chat.Message;
// import com.wangzilin.site.services.impl.ChatServiceImpl;
// import com.wangzilin.site.util.SslUtil;
// import lombok.extern.slf4j.Slf4j;
// import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.integration.annotation.ServiceActivator;
// import org.springframework.integration.channel.DirectChannel;
// import org.springframework.integration.core.MessageProducer;
// import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
// import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
// import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.MessageHandler;
//
// import javax.net.ssl.SSLSocketFactory;
//
// @Configuration
// @Slf4j
// public class MqttConfig {
//
//     final private ChatServiceImpl chatServiceImpl;
//
//     final private ObjectMapper mapper;
//
//     @Value("${mqtt.url}")
//     private String url;
//
//     @Value("${mqtt.clientId}")
//     private String clientId;
//
//     MqttPahoMessageDrivenChannelAdapter adapter;
//
//     public MqttConfig(ChatServiceImpl chatServiceImpl, ObjectMapper mapper) {
//         this.chatServiceImpl = chatServiceImpl;
//         this.mapper = mapper;
//     }
//
//     @Bean
//     public MessageChannel mqttInputChannel() {
//         return new DirectChannel();
//     }
//
//     @Bean
//     public MessageProducer inbound() throws Exception {
//         SSLSocketFactory socketFactory = SslUtil.getSocketFactory();
//         MqttConnectOptions options = new MqttConnectOptions();
//         options.setSocketFactory(socketFactory);
//         DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
//         defaultMqttPahoClientFactory.setConnectionOptions(options);
//
//         adapter =
//                 new MqttPahoMessageDrivenChannelAdapter(url, clientId,
//                         defaultMqttPahoClientFactory,
//                         "addChannel");
//         adapter.setCompletionTimeout(30000);
//         adapter.setConverter(new DefaultPahoMessageConverter());
//         adapter.setQos(2);
//         adapter.setOutputChannel(mqttInputChannel());
//         return adapter;
//     }
//
//     @Bean
//     @ServiceActivator(inputChannel = "mqttInputChannel")
//     public MessageHandler handler() {
//         return message -> {
//             String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
//             assert topic != null;
//             String payLoadJson = (String) message.getPayload();
//             try {
//                 //用户新增了一个聊天channel, 服务器应立刻订阅
//                 if (topic.equals("addChannel")) {
//                     String newChannelName = mapper.readTree(payLoadJson).path("channelName").asText();
//                     adapter.addTopic(newChannelName, 2);
//                     log.info("add topic: " + newChannelName);
//                 }
//                 //处理所有的用户channel
//                 else if (topic.startsWith("user-")) {
//                     //获得channel name: channel-前缀后面的内容就是channel的名字
//                     String channelName = topic.substring(8);
//                     Message userMessage = mapper.readValue(payLoadJson, Message.class);
//                     chatServiceImpl.saveMessage(channelName, userMessage);
//                     log.info(channelName + " " + payLoadJson);
//                 }
//             } catch (JsonProcessingException e) {
//                 log.info(topic + " " + payLoadJson);
//                 e.printStackTrace();
//             }
//         };
//     }
// }
//
