package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.model.MessageModel;
import com.***REMOVED***.site.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    private ChatDAO chatDAO;

    @Autowired
    UserService userService;


    @Autowired
    public ChatService(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

    public void saveMessage(String channel, MessageModel message) {
        //讲接收到的某个channel的消息存入数据库
        chatDAO.saveMessage(channel, message);
    }

    public List<MessageModel> getHistoryMessage(String channelName) {
        return chatDAO.findMessageByDate(new Date(), 100, channelName);
    }

    public void addNewChannel(String userName, String channelName, boolean isNewChannel) {
        if (channelName.startsWith("user-")) {
            // 如果是新创建的channel, 则服务器先订阅
            if (isNewChannel) {
                MqttPahoMessageDrivenChannelAdapter adapter =
                        BeanUtil.getBean(MqttPahoMessageDrivenChannelAdapter.class);
                adapter.getTopic();
            }
            // 将新的channel写入用户订阅列表
            userService.addUserChannel(userName, channelName);
            userService.addChannel(channelName);
        } else {
            throw new MessagingException("用户channel必须以user-开头");
        }
    }
}