package com.***REMOVED***.site.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.***REMOVED***.site.model.ChannelModel;
import com.***REMOVED***.site.model.UserProfileModel;
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

    private String COLLECTION_NAME = "profile";

    UserDAO() {
    }

    public void addUser(UserProfileModel userProfile) {
        mongoTemplateForUser.save(userProfile, "profile");
    }

    public UserProfileModel findUser(String userName) {
        return mongoTemplateForUser.findOne(new Query(Criteria.where("userId").is(userName)),
                UserProfileModel.class, "profile");
    }

    public void addUserChannel(String userName, String channelName) {
        mongoTemplateForUser.updateFirst(new Query(Criteria.where("userId").is(userName)), new Update().addToSet(
                "channels", channelName), "profile");
    }

    public void addGlobalChannel(ChannelModel channel) {
        if (mongoTemplateForUser.exists(new Query(Criteria.where("name").is(channel.name)), ChannelModel.class,
                "channels")) {
            throw new MessagingException("已存在相同用户名");
        }
        mongoTemplateForUser.save(channel, "channels");
    }

    public void updateGlobalChannel(String channelName, String newUserId) {
        mongoTemplateForUser.updateFirst(new Query(Criteria.where("name").is(channelName)), new Update().addToSet(
                "members", newUserId), "channels");
    }

    public ChannelModel findChannel(String name) {
        return mongoTemplateForUser.findOne(new Query(Criteria.where("name").is(name)), ChannelModel.class, "channels");
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
                ChannelModel.class, "channels");
        if (!deleteResult.wasAcknowledged()) {
            throw new MessagingException("deleteChannel failed");
        }
    }
}
