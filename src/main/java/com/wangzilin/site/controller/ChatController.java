package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.MessageModel;
import com.***REMOVED***.site.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    final private ChatService chatService;

    @Autowired
    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @RequestMapping(value = "/getHistory", method = RequestMethod.POST)
    public List<MessageModel> getHistory(@RequestBody Map params) {
        String channelName = (String) params.get("channelName");
        return chatService.getHistoryMessage(channelName);
    }
}
