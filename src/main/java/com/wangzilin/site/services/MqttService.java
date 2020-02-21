package com.***REMOVED***.site.services;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Member;
import java.util.Arrays;

@Component
public class MqttService implements ApplicationRunner {
    private static MqttClient client;

    //初始化参数
    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println("初始化MQTT连接");
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
            client.setCallback(new TopMsgCallback(client, options, messageTopic, qos));
            client.connect(options);
            client.subscribe("chat", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TopMsgCallback implements MqttCallback {

    //这些东西都是为了重连用的
    private MqttClient client;
    private MqttConnectOptions options;
    private String[] topic;
    private int[] qos;

    public TopMsgCallback() {}

    public TopMsgCallback(MqttClient client, MqttConnectOptions options,String[] topic,int[] qos) {
        this.client = client;
        this.options = options;
        this.topic=topic;
        this.qos=qos;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("正在重连");
        while (true) {
            try {
                Thread.sleep(3000);
                client.connect(options);
                client.subscribe(topic, qos);
                System.out.println("重新连接成功");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(topic);
        System.out.println(Arrays.toString(message.getPayload()));
    }



    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
