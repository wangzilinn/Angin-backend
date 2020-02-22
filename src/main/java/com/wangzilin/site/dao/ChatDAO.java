package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.MessageModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Repository
public class ChatDAO {
    @Resource
    private MongoTemplate mongoTemplateForChat;
    final private String COLLECTION_NAME = "chat";

    ChatDAO(){}

    public void saveMessage(MessageModel message) {
        mongoTemplateForChat.save(message, COLLECTION_NAME);
    }

}
