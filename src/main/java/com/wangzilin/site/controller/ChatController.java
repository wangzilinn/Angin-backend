package com.***REMOVED***.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.chat.ChatChannel;
import com.***REMOVED***.site.model.chat.ChatMessage;
import com.***REMOVED***.site.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ObjectMapper mapper;

    /**
     * 获得频道历史消息
     *
     * @param headers include channelName
     * @return chatMassage list
     */
    @RequestMapping(value = "/channelHistory", method = RequestMethod.GET)
    public List<ChatMessage> getHistory(@RequestHeader Map<String, Object> headers) {
        String channelName = (String) headers.get("channelName");
        return chatService.getHistoryMessage(channelName);
    }


    /**
     * 获得用户订阅的频道
     *
     * @param headers include userId
     * @return user channel list
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.GET)
    public List<ChatChannel> getUserChannelList(@RequestHeader Map<String, Object> headers) {
        String userName = (String) headers.get("userId");
        return chatService.getUserChannels(userName);
    }


    /**
     * 用户新建频道
     *
     * @param body request body
     * @return ..
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.POST)
    public ResponseEntity<String> subscribeChannel(@RequestBody Map<String, Object> body) {
        String userId = (String) body.get("userId");
        ChatChannel channel = mapper.convertValue(body.get("channel"), ChatChannel.class);
        boolean isNewChannel = (boolean) body.get("newChannel");
        try {
            chatService.subscribeNewChannel(userId, channel, isNewChannel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 用户删除频道
     *
     * @param body request body
     * @return ..
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.DELETE)
    public ResponseEntity<String> unsubscribeChannel(@RequestBody Map<String, Object> body) {
        String userId = (String) body.get("userId");
        try {
            String channelName = (String) body.get("channelName");
            chatService.unsubscribeChannel(userId, channelName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.NOT_FOUND);//404
        }
    }
}
