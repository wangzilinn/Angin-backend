package com.***REMOVED***.site.cards;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@Repository
public interface CardRepository extends MongoRepository<DBCard, String> {
    Page<DBCard> findByExpireDateLessThan(Date date, Pageable pageable);
    List<DBCard> findByExpireDateLessThan(Date date);
    DBCard findByExpireDate(Date date);
    DBCard findByKeyContains(String key);
    //替换过期日期

    default DBCard findLatestByExpireDateLessThan(Date date){
        PageRequest request =
                PageRequest.of(0, 1, Sort.Direction.DESC,"expireDate");
        return findByExpireDateLessThan(date, request).getContent().get(0);
    }
}
