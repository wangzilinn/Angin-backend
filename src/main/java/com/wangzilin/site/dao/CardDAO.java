package com.wangzilin.site.dao;

import com.wangzilin.site.model.card.DBCard;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class CardDAO {

    @Resource
    private MongoTemplate mongoTemplateForCard;
    final private String COLLECTION_NAME = "card";

    public CardDAO() {
    }

    public List<DBCard> findByExpireDateLessThan(Date date) {
        return mongoTemplateForCard.find(new Query(Criteria.where("expireDate").lte(date).and("status").ne(-1)),
                DBCard.class, COLLECTION_NAME);
    }

    public List<DBCard> findByExpireDateLessThan(Date date, int limit) {
        Query query = new Query(Criteria.where("expireDate").lte(date).and("status").ne(-1));
        return mongoTemplateForCard.find(query.limit(limit), DBCard.class, COLLECTION_NAME);
    }

    public DBCard findByKeyContains(String key){
        key = String.format("^.*%s.*$", key);
        return mongoTemplateForCard.findOne(new Query(Criteria.where("key").regex(key)), DBCard.class, COLLECTION_NAME);
    }

    public List<DBCard> findByStatusEqualTo(int status) {
        return mongoTemplateForCard.find(new Query(Criteria.where("status").is(status)), DBCard.class, COLLECTION_NAME);
    }

    public List<DBCard> findByStatusEqualTo(int status, int limit) {
        Query query = new Query(Criteria.where("status").is(status));
        return mongoTemplateForCard.find(query.limit(limit), DBCard.class, COLLECTION_NAME);
    }


    public void saveCard(DBCard dbCard) {
        mongoTemplateForCard.save(dbCard, COLLECTION_NAME);
    }


    public void updateCardItem(String key, Map<String, Object> updateMap) {
        Update update = new Update();
        for (Map.Entry<String, Object> item : updateMap.entrySet()) {
            update.set(item.getKey(), item.getValue());
        }

        key = String.format("^.*%s.*$", key);
        Query query = new Query(Criteria.where("key").regex(key));

        mongoTemplateForCard.updateFirst(query, update, COLLECTION_NAME);
    }

    public void deleteCard(String key) {
        key = String.format("^.*%s.*$", key);
        Query query = new Query(Criteria.where("key").regex(key));
        mongoTemplateForCard.remove(query, COLLECTION_NAME);
    }



}
