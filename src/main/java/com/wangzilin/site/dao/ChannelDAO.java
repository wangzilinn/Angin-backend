package com.wangzilin.site.dao;

import com.wangzilin.site.model.chat.Channel;
import com.wangzilin.site.model.chat.UserChannel;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 5:16 PM 5/11/2020
 * @Modified By:wangzilinn@gmail.com
 */
@NoArgsConstructor
@Repository
public class ChannelDAO {
    @Resource
    private MongoTemplate mongoTemplateForChat;
    final private String CHANNEL_COLLECTION = "channel";
    final private String USER_CHANNEL_COLLECTION = "userChannel";

    public void addChannel(Channel channel) {
        mongoTemplateForChat.save(channel, CHANNEL_COLLECTION);
    }

    /**
     * @return void
     * @Author wangzilin
     * @Description
     * @Date 7:26 PM 5/11/2020
     * @Param [chatChannel]
     **/
    public void updateChannelName(String channelName, String newName) {
        mongoTemplateForChat.updateFirst(
                new Query(Criteria.where("channelName").is(channelName)),
                new Update().set("name", newName),
                CHANNEL_COLLECTION);
    }

    public void addChannelUser(String channelName, String userName) {
        mongoTemplateForChat.updateFirst(
                new Query(Criteria.where("channelName").is(channelName)),
                new Update().addToSet("usernames", userName),
                CHANNEL_COLLECTION
        );
    }

    public void deleteChannelUser(String channelName, String userName) {
        mongoTemplateForChat.updateFirst(
                new Query(Criteria.where("channelName").is(channelName)),
                new Update().pull("usernames", userName),
                USER_CHANNEL_COLLECTION
        );
    }

    public void deleteChannel(String channelName) {
        mongoTemplateForChat.remove(
                new Query(Criteria.where("channelName").is(channelName)),
                CHANNEL_COLLECTION
        );
    }

    public Channel findChannel(String channelName) {
        return mongoTemplateForChat.findOne(
                new Query(Criteria.where("channelName").is(channelName)),
                Channel.class,
                CHANNEL_COLLECTION
        );
    }

    /**
     * @return void
     * @Author wangzilin
     * @Description userChannel表中指定用户增加频道
     * @Date 7:28 PM 5/11/2020
     * @Param [userName, channelName]
     **/
    public void addUserChannel(String userName, String channelName) {
        mongoTemplateForChat.updateFirst(
                new Query(Criteria.where("userName").is(userName)),
                new Update().addToSet("channels", channelName),
                USER_CHANNEL_COLLECTION
        );
    }

    /**
     * @return void
     * @Author wangzilin
     * @Description userChannel表中指定用户删除频道
     * @Date 7:31 PM 5/11/2020
     * @Param [username, channelName]
     **/
    public void deleteUserChannel(String username, String channelName) {
        mongoTemplateForChat.updateFirst(
                new Query(Criteria.where("username").is(username)),
                new Update().pull("channels", channelName),
                USER_CHANNEL_COLLECTION
        );
    }

    public UserChannel findUserChannel(String username) {
        return mongoTemplateForChat.findOne(
                new Query(Criteria.where("username").is(username)),
                UserChannel.class,
                USER_CHANNEL_COLLECTION
        );
    }

}
