package com.wangzilin.site.dao;

import com.wangzilin.site.model.chat.Message;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
@NoArgsConstructor
public class ChatDAO {
    @Resource
    private MongoTemplate mongoTemplateForChat;

    public void saveMessage(String collectionName, Message message) {
        mongoTemplateForChat.save(message, collectionName);
    }

    public List<Message> findMessageByDate(Date since, int limit, String channelName) {
        Query query = new Query(Criteria.where("dateTime").lte(since));
        return mongoTemplateForChat.find(query.limit(limit), Message.class, channelName);
    }
}
