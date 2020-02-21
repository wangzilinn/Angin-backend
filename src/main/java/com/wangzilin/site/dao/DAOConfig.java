package com.***REMOVED***.site.dao;


import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import com.mongodb.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import java.util.Collections;

@Configuration
public class DAOConfig {

    @Bean
    @Primary
    public MongoTemplate mongoTemplateForCard() throws Exception {
        MongoCredential mongoCredential = MongoCredential.createCredential("***REMOVED***", "admin", "***REMOVED***".toCharArray());
        ServerAddress serverAddress = new ServerAddress("***REMOVED***", 27017);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)), "back_end");
    }

    @Bean
    public MongoTemplate mongoTemplateForChat() throws Exception {
        MongoCredential mongoCredential = MongoCredential.createPlainCredential("***REMOVED***", "admin", "***REMOVED***".toCharArray());
        ServerAddress serverAddress = new ServerAddress("***REMOVED***", 27017);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)), "chat");
    }

}
