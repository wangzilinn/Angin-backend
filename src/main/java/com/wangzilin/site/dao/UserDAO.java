package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.ChannelModel;
import com.***REMOVED***.site.model.UserProfileModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        mongoTemplateForUser.save(userProfile, COLLECTION_NAME);
    }

    public UserProfileModel findUser(String userName) {
        return mongoTemplateForUser.findOne(new Query(Criteria.where("userName").is(userName)),
                UserProfileModel.class, COLLECTION_NAME);
    }

    public void addUserChannel(String userName, String channelName) {
        mongoTemplateForUser.updateFirst(new Query(Criteria.where("userName").is(userName)), new Update().addToSet(
                "channels", channelName), COLLECTION_NAME);
    }

    public void addChannel(ChannelModel channel) {
        mongoTemplateForUser.upsert(new Query(Criteria.where("name").is(channel.name)), new Update().set("name",
                channel.name), "channels");
    }
}
