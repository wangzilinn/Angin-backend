package com.***REMOVED***.site.services;

import com.***REMOVED***.site.dao.ChatDAO;
import com.***REMOVED***.site.dao.UserDAO;
import com.***REMOVED***.site.model.chat.ChatChannel;
import com.***REMOVED***.site.model.chat.ChatMessage;
import com.***REMOVED***.site.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    UserDAO userDAO;


    public void saveMessage(String channel, ChatMessage message) {
        //讲接收到的某个channel的消息存入数据库
        chatDAO.saveMessage(channel, message);
    }

    public List<ChatMessage> getHistoryMessage(String channelName) {
        return chatDAO.findMessageByDate(new Date(), 100, channelName);
    }

    public void subscribeNewChannel(String userName, ChatChannel channel, boolean isNewChannel) {
        if (channel.name.startsWith("user-")) {
            if (isNewChannel) {
                // 如果是新创建的channel, 则服务器先订阅
                MqttPahoMessageDrivenChannelAdapter adapter =
                        BeanUtil.getBean(MqttPahoMessageDrivenChannelAdapter.class);
                adapter.addTopic(channel.name);
                // 之后将channel加入到数据库
                userDAO.addGlobalChannel(channel);
            } else {
                //否则仅更新原有的channel列表
                userDAO.updateGlobalChannel(channel.name, userName);
            }
            // 将新的channel写入用户订阅列表
            userDAO.addUserChannel(userName, channel.name);
        } else {
            throw new MessagingException("用户channel必须以user-开头");
        }
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