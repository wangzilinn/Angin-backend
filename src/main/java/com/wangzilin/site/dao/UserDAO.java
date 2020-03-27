package com.***REMOVED***.site.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.***REMOVED***.site.model.chat.ChatChannel;
import com.***REMOVED***.site.model.user.UserProfile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDAO {

    @Resource
    private MongoTemplate mongoTemplateForUser;

    UserDAO() {
    }

    public void addUser(UserProfile userProfile) {
        mongoTemplateForUser.save(userProfile, "profile");
    }

    public UserProfile findUser(String userId) {
        UserProfile userProfile = null;
        try {
            userProfile = mongoTemplateForUser.findOne(new Query(Criteria.where("userId").is(userId)),
                    UserProfile.class, "profile");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userProfile;
    }

    public void addUserChannel(String userName, String channelName) {
        mongoTemplateForUser.updateFirst(new Query(Criteria.where("userId").is(userName)), new Update().addToSet(
                "channels", channelName), "profile");
    }

    public void addGlobalChannel(ChatChannel channel) {
        if (mongoTemplateForUser.exists(new Query(Criteria.where("name").is(channel.name)), ChatChannel.class,
                "channels")) {
            throw new MessagingException("已存在相同用户名");
        }
        mongoTemplateForUser.save(channel, "channels");
    }

    public void updateGlobalChannel(String channelName, String newUserId) {
        mongoTemplateForUser.updateFirst(new Query(Criteria.where("name").is(channelName)), new Update().addToSet(
                "members", newUserId), "channels");
    }

    public ChatChannel findChannel(String name) {
        return mongoTemplateForUser.findOne(new Query(Criteria.where("name").is(name)), ChatChannel.class, "channels");
    }


    public void removeGlobalChannelUser(String userName, String channelName) {
        UpdateResult updateResult =
                mongoTemplateForUser.updateFirst(new Query(Criteria.where("name").is(channelName)), new Update().pull(
                        "members", userName), "channels");
        if (!updateResult.wasAcknowledged()) {
            throw new MessagingException("remove Global Channel User failed ");
        }
    }

    public void removeUserChannel(String userId, String channelName) {
        UpdateResult updateResult = mongoTemplateForUser.updateFirst(new Query(Criteria.where("userId").is(userId)),
                new Update().pull(
                        "channels", channelName), "profile");
        if (!updateResult.wasAcknowledged()) {
            throw new MessagingException("remove User Channel failed");
        }
    }

    public void deleteChannel(String channelName) {
        DeleteResult deleteResult = mongoTemplateForUser.remove(new Query(Criteria.where("name").is(channelName)),
                ChatChannel.class, "channels");
        if (!deleteResult.wasAcknowledged()) {
            throw new MessagingException("deleteChannel failed");
        }
    }
}
