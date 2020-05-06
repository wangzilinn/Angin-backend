package com.***REMOVED***.site.services.impl;

import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.dao.UserDAO;
import com.***REMOVED***.site.model.chat.ChatChannel;
import com.***REMOVED***.site.model.chat.ChatMessage;
import com.***REMOVED***.site.util.BeanUtil;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    final private ChatDAO chatDAO;

    final private UserDAO userDAO;


    public ChatService(ChatDAO chatDAO, UserDAO userDAO) {
        this.chatDAO = chatDAO;
        this.userDAO = userDAO;
    }

    public void saveMessage(String channel, ChatMessage message) {
        //讲接收到的某个channel的消息存入数据库
        chatDAO.saveMessage(channel, message);
    }

    public List<ChatMessage> getHistoryMessage(String channelName) {
        return chatDAO.findMessageByDate(new Date(), 100, channelName);
    }


    public void createAndsSubscribeChannel(String userId, String channelName) {
        //创建一个空的channel
        ChatChannel channel = new ChatChannel(channelName, userId);
        // 新创建的channel, 服务器先订阅
        MqttPahoMessageDrivenChannelAdapter adapter =
                BeanUtil.getBean(MqttPahoMessageDrivenChannelAdapter.class);
        adapter.addTopic(channel.name);
        // 之后将channel加入到channel数据库
        userDAO.addGlobalChannels(channel);
        //最后更新用户订阅列表
        userDAO.updateUserChannel(userId, channel.name);
    }

    public void subscribeChannel(String userId, String channelName) {
        //更新原有的channel列表
        userDAO.updateGlobalChannels(channelName, userId);
        // 将新的channel写入用户订阅列表
        userDAO.updateUserChannel(userId, channelName);
    }

    public void unsubscribeChannel(String userId, String channelName) {
        //判断该频道是否还有人订阅
        ChatChannel chatChannel = userDAO.findChannel(channelName);
        if (chatChannel.members.size() == 1) {
            //仅有一人订阅, 且取订的话, 这个频道就可以消失了
            userDAO.deleteChannel(channelName);
            MqttPahoMessageDrivenChannelAdapter adapter =
                    BeanUtil.getBean(MqttPahoMessageDrivenChannelAdapter.class);
            adapter.removeTopic(channelName);
        }
        userDAO.removeGlobalChannelUser(userId, channelName);
        userDAO.removeUserChannel(userId, channelName);
    }

    public List<ChatChannel> getUserChannels(String userId) {
        List<ChatChannel> chatChannels = new ArrayList<>();
        userDAO.findUser(userId).getChannels().forEach(stringChannel -> chatChannels.add(userDAO.findChannel(stringChannel)));
        return chatChannels;
    }
}