package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.ChatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatDAO chatDAO;



//    public ChatService(ChatDAO chatDAO){
//        this.chatDAO = chatDAO;
//    }

    public ChatService() {
    }

    public void saveMessage(String message) {
        chatDAO.saveMessage(message);
    }
}
