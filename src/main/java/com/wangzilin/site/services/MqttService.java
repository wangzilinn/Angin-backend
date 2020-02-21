package com.***REMOVED***.site.services;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MqttService implements ApplicationRunner {
    private static MqttClient client;
    @Autowired
    private MessageCallback messageCallback;

    //初始化参数
    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println("MQTT连接");
        connect();
    }

    private void connect() throws Exception {
        client = new MqttClient("tcp://***REMOVED***:1883", "springboot", new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setConnectionTimeout(30); //连接超时时间
        options.setKeepAliveInterval(20); //心跳包间隔
        try {
            String[] messageTopic = {"chat"};
            int[] qos = {2};
            client.setCallback(messageCallback);
            client.connect(options);
            client.subscribe("chat", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

