package com.***REMOVED***.site.dao;

import com.***REMOVED***.site.cards.DBCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

public class CardDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DBCard> findByExpireDateLessThan(Date date) {
        return mongoTemplate.find(new Query(Criteria.where("expireDate").lte(date)), DBCard.class);
    }

//    public DBCard

}
