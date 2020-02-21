package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.model.DBCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class CardDAO {

    private MongoTemplate mongoTemplate;
    final private String COLLECTION_NAME = "card";

    @Autowired
    CardDAO(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<DBCard> findByExpireDateLessThan(Date date) {
        return mongoTemplate.find(new Query(Criteria.where("expireDate").lte(date).and("status").ne(-1)), DBCard.class, COLLECTION_NAME);
    }

    public List<DBCard> findByExpireDateLessThan(Date date, int limit){
        Query query = new Query(Criteria.where("expireDate").lte(date).and("status").ne(-1));
        return mongoTemplate.find(query.limit(limit), DBCard.class, COLLECTION_NAME);
    }

    public DBCard findByKeyContains(String key){
        key = String.format("^.*%s.*$", key);
        return mongoTemplate.findOne(new Query(Criteria.where("key").regex(key)), DBCard.class, COLLECTION_NAME);
    }

    public List<DBCard> findByStatusEqualTo(int status) {
        return mongoTemplate.find(new Query(Criteria.where("status").is(status)), DBCard.class, COLLECTION_NAME);
    }

    public List<DBCard> findByStatusEqualTo(int status, int limit) {
        Query query = new Query(Criteria.where("status").is(status));
        return mongoTemplate.find(query.limit(limit), DBCard.class, COLLECTION_NAME);
    }


    public void saveCard(DBCard dbCard) {
        mongoTemplate.save(dbCard, COLLECTION_NAME);
    }


    public void updateCardItem(String key, Map<String, Object> updateMap) {
        Update update = new Update();
        for (Map.Entry<String, Object> item : updateMap.entrySet()) {
            update.set(item.getKey(), item.getValue());
        }

        key = String.format("^.*%s.*$", key);
        Query query = new Query(Criteria.where("key").regex(key));

        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
    }

    public void deleteCard(String key) {
        key = String.format("^.*%s.*$", key);
        Query query = new Query(Criteria.where("key").regex(key));
        mongoTemplate.remove(query, COLLECTION_NAME);
    }



}
