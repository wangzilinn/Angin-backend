package com.***REMOVED***.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.chat.ChatChannel;
import com.***REMOVED***.site.model.chat.ChatMessage;
import com.***REMOVED***.site.services.ChatService;
import com.***REMOVED***.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

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


    //获得频道历史消息
    @RequestMapping(value = "/channelHistory", method = RequestMethod.GET)
    public List<ChatMessage> getHistory(@RequestHeader Map<String, Object> params) {
        String channelName = (String) params.get("channelName");
        return chatService.getHistoryMessage(channelName);
    }

    //获得用户订阅的频道
    @RequestMapping(value = "/userChannel", method = RequestMethod.GET)
    public List<ChatChannel> getUserChannelList(@RequestHeader Map<String, Object> params) {
        String userName = (String) params.get("userId");
        return chatService.getUserChannels(userName);
    }

    //用户新建频道
    @RequestMapping(value = "/userChannel", method = RequestMethod.POST)
    public ResponseEntity<String> subscribeChannel(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        ChatChannel channel = mapper.convertValue(params.get("channel"), ChatChannel.class);
        boolean isNewChannel = (boolean) params.get("newChannel");
        try {
            chatService.subscribeNewChannel(userId, channel, isNewChannel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    //用户删除频道
    @RequestMapping(value = "/userChannel", method = RequestMethod.DELETE)
    public ResponseEntity<String> unsubscribeChannel(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        try {
            String channelName = (String) params.get("channelName");
            chatService.unsubscribeChannel(userId, channelName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.NOT_FOUND);//404
        }
    }
}
