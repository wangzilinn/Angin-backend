package com.***REMOVED***.site.cards;

import org.springframework.data.mongodb.repository.MongoRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    public List<Card> findByExpireDateGreaterThan(Date date);
    public Card findByExpireDate(Date date);
    public Card findByFront(String frontString);
}
