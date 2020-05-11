package com.***REMOVED***.site.services.impl;

import com.***REMOVED***.site.dao.ChannelDAO;
import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.model.chat.Channel;
import com.***REMOVED***.site.model.chat.Message;
import com.***REMOVED***.site.services.ChatService;
import com.***REMOVED***.site.util.BeanUtil;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    final private ChatDAO chatDAO;
    final private ChannelDAO channelDAO;


    public ChatServiceImpl(ChatDAO chatDAO, ChannelDAO channelDAO) {
        this.chatDAO = chatDAO;
        this.channelDAO = channelDAO;
    }

    @Override
    public void saveMessage(String channelName, Message message) {
        //讲接收到的某个channel的消息存入数据库
        chatDAO.saveMessage(channelName, message);
    }

    @Override
    public List<Message> getChannelHistory(String channelName) {
        return chatDAO.findMessageByDate(new Date(), 100, channelName);
    }


    @Override
    public void createAndSubscribeChannel(String username, String channelName) {
        //创建一个空的channel
        Channel channel = new Channel(channelName, username);
        // 新创建的channel, 服务器先订阅
        MqttPahoMessageDrivenChannelAdapter adapter =
                BeanUtil.getBean(MqttPahoMessageDrivenChannelAdapter.class);
        adapter.addTopic(channel.getId().toString());
        // 之后将channel加入到channel数据库
        channelDAO.addChannel(channel);
        //最后更新用户订阅列表
        channelDAO.addUserChannel(username, channel.getName());
    }

    @Override
    public void subscribeChannel(String userName, String channelName) {
        //更新原有的channel列表
        channelDAO.addChannelUser(channelName, userName);
        // 将新的channel写入用户订阅列表
        channelDAO.addUserChannel(userName, channelName);
    }

    @Override
    public void unsubscribeChannel(String username, String channelName) {
        //判断该频道是否还有人订阅
        Channel channel = channelDAO.findChannel(channelName);
        if (channel.getUsernames().size() == 1) {
            //仅有一人订阅, 且取订的话, 这个频道就可以消失了
            channelDAO.deleteChannel(channelName);
            MqttPahoMessageDrivenChannelAdapter adapter =
                    BeanUtil.getBean(MqttPahoMessageDrivenChannelAdapter.class);
            adapter.removeTopic(channel.getId().toString());
        }
        channelDAO.deleteChannelUser(channelName, username);
        channelDAO.deleteUserChannel(username, channelName);
    }

    public List<Channel> getUserChannels(String username) {
        List<Channel> channels = new ArrayList<>();
        channelDAO.findUserChannel(username).getChannels().forEach(
                channelName -> channels.add(channelDAO.findChannel(channelName))
        );
        return channels;
    }
}