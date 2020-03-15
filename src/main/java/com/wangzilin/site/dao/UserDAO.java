package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.UserProfileModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDAO {

    @Resource
    private MongoTemplate mongoTemplateForUser;

    private String COLLECTION_NAME = "user";

    UserDAO() {
    }

    public void addUser(UserProfileModel userProfile) {
        mongoTemplateForUser.save(userProfile, COLLECTION_NAME);
    }

    public UserProfileModel findUser(String userName) {
        return mongoTemplateForUser.findOne(new Query(Criteria.where("userName").is(userName)),
                UserProfileModel.class, COLLECTION_NAME);
    }
}
