package com.***REMOVED***.site.services;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCallback implements MqttCallback {

    @Autowired
    ChatService chatService;
    //这些东西都是为了重连用的
    private MqttClient client;
    private MqttConnectOptions options;
    private String[] topic;
    private int[] qos;

    public MessageCallback() {}

//    public MessageCallback(MqttClient client, MqttConnectOptions options, String[] topic, int[] qos) {
//        this.client = client;
//        this.options = options;
//        this.topic=topic;
//        this.qos=qos;
//    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("断开");
//        while (true) {
//            try {
//                Thread.sleep(3000);
//                client.connect(options);
//                client.subscribe(topic, qos);
//                System.out.println("重新连接成功");
//                break;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(topic);
        String data = new String(message.getPayload());
        System.out.println(data);
        new ChatService();
        chatService.saveMessage(data);
    }



    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
