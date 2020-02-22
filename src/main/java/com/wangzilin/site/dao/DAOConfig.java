package com.***REMOVED***.site.dao;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;

@Configuration
public class DAOConfig {

    @Bean
    @Primary
    public MongoTemplate mongoTemplateForCard() {
        MongoCredential mongoCredential = MongoCredential.createCredential("***REMOVED***", "admin", "***REMOVED***".toCharArray());
        ServerAddress serverAddress = new ServerAddress("***REMOVED***", 27017);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)), "back_end");
    }

    @Bean
    public MongoTemplate mongoTemplateForChat() {
        MongoCredential mongoCredential = MongoCredential.createCredential("***REMOVED***", "admin", "***REMOVED***".toCharArray());
        ServerAddress serverAddress = new ServerAddress("***REMOVED***", 27017);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)), "chat");
    }

}
