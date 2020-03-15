package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.MessageModel;
import com.***REMOVED***.site.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    final private ChatService chatService;

    @Autowired
    MessageController(ChatService chatService) {
        this.chatService = chatService;
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.GET)
    public List<MessageModel> getHistory() {
        return chatService.getHistoryMessage();
    }
}
