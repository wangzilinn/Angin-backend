package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.MessageModel;
import com.***REMOVED***.site.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    final private MessageService messageService;

    @Autowired
    MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.POST)
    public List<MessageModel> getHistory() {
        return messageService.getHistoryMessage();
    }
}
