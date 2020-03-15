package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    final private ChatService chatService;

    @Autowired
    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

//    @RequestMapping(value = "/getHistory", method = RequestMethod.POST)
//    public List<MessageModel> getHistory() {
//        return chatService.getHistoryMessage("123");
//    }
}
