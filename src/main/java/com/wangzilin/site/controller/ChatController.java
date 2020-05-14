package com.wangzilin.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangzilin.site.model.chat.Channel;
import com.wangzilin.site.model.chat.Message;
import com.wangzilin.site.services.ChatService;
import com.wangzilin.site.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    private ChatService chatService;

    private ObjectMapper mapper;

    public ChatController(ChatService chatService, ObjectMapper mapper) {
        this.chatService = chatService;
        this.mapper = mapper;
    }

    /**
     * 获得频道历史消息
     *
     * @param headers include channelName
     * @return chatMassage list
     */
    @RequestMapping(value = "/channelHistory", method = RequestMethod.GET)
    public List<Message> getHistory(@RequestHeader Map<String, Object> headers) {
        String channelName = (String) headers.get("channel-name");
        return chatService.getChannelHistory(channelName);
    }


    /**
     * 获得用户订阅的所有频道列表
     *
     * @param headers include userId
     * @return user channel list.
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.GET)
    public List<Channel> getUserChannelList(@RequestHeader Map<String, Object> headers) {
        //自定义的header均会被转为小写
        String userId = JwtUtil.getUsernameFromToken((String) headers.get("authorization"));
        return chatService.getUserChannels(userId);
    }


    /**
     * 用户新建频道
     *
     * @param headers .
     * @return .
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.POST)
    public ResponseEntity<String> createAndSubscribeChannel(@RequestHeader Map<String, String> headers) {
        String userId = JwtUtil.getUsernameFromToken(headers.get("authorization"));
        String channelName = headers.get("channel-name");
        try {
            chatService.createAndSubscribeChannel(userId, channelName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 用户订阅频道
     *
     * @param headers .
     * @return .
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.PUT)
    public ResponseEntity<String> subscribeChannel(@RequestHeader Map<String, String> headers) {
        String userId = JwtUtil.getUsernameFromToken(headers.get("authorization"));
        String channelName = headers.get("channel-name");
        try {
            chatService.subscribeChannel(userId, channelName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 用户删除订阅的频道
     *
     * @param headers .
     * @return .
     */
    @RequestMapping(value = "/userChannel", method = RequestMethod.DELETE)
    public ResponseEntity<String> unsubscribeChannel(@RequestHeader Map<String, String> headers) {
        String username = JwtUtil.getUsernameFromToken((String) headers.get("authorization"));
        try {
            String channelName = headers.get("channelId");
            chatService.unsubscribeChannel(username, channelName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        } catch (MessagingException m) {
            return new ResponseEntity<>(m.toString(), HttpStatus.NOT_FOUND);//404
        }
    }
}
