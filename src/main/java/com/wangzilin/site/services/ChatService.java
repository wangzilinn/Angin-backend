package com.wangzilin.site.services;

import com.wangzilin.site.model.chat.Channel;
import com.wangzilin.site.model.chat.Message;

import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 5:39 PM 5/11/2020
 * @Modified By:wangzilinn@gmail.com
 */
public interface ChatService {

    /**
     * @return void
     * @Author wangzilin
     * @Description 保存一条消息
     * @Date 5:46 PM 5/11/2020
     * @Param [channelName, message]
     **/
    void saveMessage(String channelName, Message message);

    List<Message> getChannelHistory(String channelName);

    void createAndSubscribeChannel(String username, String channelName);

    void subscribeChannel(String username, String channelName);

    void unsubscribeChannel(String username, String channelName);

    List<Channel> getUserChannels(String username);
}
