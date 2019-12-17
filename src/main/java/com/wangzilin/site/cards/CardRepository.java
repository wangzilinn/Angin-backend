package com.***REMOVED***.site.cards;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    public Page<Card> findByExpireDateGreaterThan(Date date, Pageable pageable);
    public List<Card> findByExpireDateGreaterThan(Date date);
    public Card findByExpireDate(Date date);
    public Card findByFront(String frontString);

    default Card findLatestByExpireDateGreaterThan(Date date){
        PageRequest request =
                PageRequest.of(0, 1, Sort.Direction.DESC,"expireDate");
        return findByExpireDateGreaterThan(date, request).getContent().get(0);
    }
}
