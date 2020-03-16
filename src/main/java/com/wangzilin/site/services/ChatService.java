package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    private ChatDAO chatDAO;


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

}
