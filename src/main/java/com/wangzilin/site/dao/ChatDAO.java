package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.chat.ChatMessage;
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

    ChatDAO() {
    }

    public void saveMessage(String collectionName, ChatMessage message) {
        mongoTemplateForChat.save(message, collectionName);
    }

    public List<ChatMessage> findMessageByDate(Date since, int limit, String channelName) {
        Query query = new Query(Criteria.where("dateTime").lte(since));
        return mongoTemplateForChat.find(query.limit(limit), ChatMessage.class, channelName);
    }
}
