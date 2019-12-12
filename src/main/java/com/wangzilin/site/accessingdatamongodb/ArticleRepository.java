package com.***REMOVED***.site.accessingdatamongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
    Article findByTitle(String title);
}
