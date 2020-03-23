package com.***REMOVED***.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.ChannelModel;
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

    @Autowired
    private ObjectMapper mapper;


    @RequestMapping(value = "/channelHistory", method = RequestMethod.POST)
    public List<MessageModel> getHistory(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userId, password)) {
            String channelName = (String) params.get("channelName");
            return chatService.getHistoryMessage(channelName);
        }
        return null;
    }

    @RequestMapping(value = "/userChannel", method = RequestMethod.GET)
    public List<ChannelModel> getUserChannelList(@RequestBody Map<String, Object> params) {
        String userName = (String) params.get("userId");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userName, password)) {
            return userService.getUserChannels(userName);
        }
        return null;
    }

    @RequestMapping(value = "/userChannel", method = RequestMethod.POST)
    public ResponseEntity<String> subscribeChannel(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userId, password)) {
            ChannelModel channel = mapper.convertValue(params.get("channel"), ChannelModel.class);
            boolean isNewChannel = (boolean) params.get("newChannel");
            try {
                chatService.subscribeNewChannel(userId, channel, isNewChannel);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (MessagingException m) {
                return new ResponseEntity<>(m.toString(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/userChannel", method = RequestMethod.DELETE)
    public ResponseEntity<String> unsubscribeChannel(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        if (userService.authenticateUser(userId, password)) {
            try {
                String channelName = (String) params.get("channelName");
                chatService.unsubscribeChannel(userId, channelName);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
            } catch (MessagingException m) {
                return new ResponseEntity<>(m.toString(), HttpStatus.NOT_FOUND);//404
            }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
