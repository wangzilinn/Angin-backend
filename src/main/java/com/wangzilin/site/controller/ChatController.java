package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.MessageModel;
import com.***REMOVED***.site.services.ChatService;
import com.***REMOVED***.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/channelHistory", method = RequestMethod.POST)
    public List<MessageModel> getHistory(@RequestBody Map params) {
        String userName = (String) params.get("userName");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userName, password)) {
            String channelName = (String) params.get("channelName");
            return chatService.getHistoryMessage(channelName);
        }
        return null;
    }

    @RequestMapping(value = "/userChannelList", method = RequestMethod.POST)
    public List<String> getUserChannelList(@RequestBody Map params) {
        String userName = (String) params.get("userName");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userName, password)) {
            return userService.getUserChannels(userName);
        }
        return null;
    }

    @RequestMapping(value = "/newChannel", method = RequestMethod.POST)
    public ResponseEntity setNewChannel(@RequestBody Map params) {
        String userName = (String) params.get("userName");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userName, password)) {
            String channelName = (String) params.get("channelName");
            boolean isNewChannel = (boolean) params.get("newChannel");
            try {
                chatService.addNewChannel(userName, channelName, isNewChannel);
                return new ResponseEntity(HttpStatus.CREATED);
            } catch (MessagingException m) {
                return new ResponseEntity(m.toString(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
