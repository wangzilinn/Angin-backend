package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    private ChatDAO chatDAO;

    @Autowired
    public MessageService(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

    public void saveMessage(MessageModel message) {
        chatDAO.saveMessage(message);
    }

    public void saveMessage(String channel, MessageModel message) {
        //TODO:
    }

    public List<MessageModel> getHistoryMessage() {
        return chatDAO.findByDate(new Date(), 100);
    }
}
