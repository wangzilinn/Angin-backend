package com.***REMOVED***.site.cards;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
@Repository
public interface CardRepository extends MongoRepository<Card, String> {
    Page<Card> findByExpireDateLessThan(Date date, Pageable pageable);
    List<Card> findByExpireDateLessThan(Date date);
    Card findByExpireDate(Date date);
    Card findByKeyContains(String key);
    //替换过期日期

    default Card findLatestByExpireDateLessThan(Date date){
        PageRequest request =
                PageRequest.of(0, 1, Sort.Direction.DESC,"expireDate");
        return findByExpireDateLessThan(date, request).getContent().get(0);
    }
}
