package com.***REMOVED***.site.cards;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface CardRepository extends MongoRepository<Card, String> {
    public Card findByExpireDate(Date date);
    public Card findByFront(String frontString);
}
