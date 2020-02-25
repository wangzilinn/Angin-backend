package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.MessageModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class ChatDAO {
    @Resource
    private MongoTemplate mongoTemplateForChat;
    final private String COLLECTION_NAME = "chat";

    ChatDAO() {
    }

    public void saveMessage(MessageModel message) {
        mongoTemplateForChat.save(message, COLLECTION_NAME);
    }

    public List<MessageModel> findByDate(Date since, int limit) {
        Query query = new Query(Criteria.where("dateTime").lte(since));
        return mongoTemplateForChat.find(query.limit(limit), MessageModel.class, COLLECTION_NAME);
    }
}
