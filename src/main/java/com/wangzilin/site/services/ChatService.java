package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {


    private ChatDAO chatDAO;


    @Autowired
    public ChatService(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }


    public void saveMessage(MessageModel message) {
        chatDAO.saveMessage(message);
    }
}
