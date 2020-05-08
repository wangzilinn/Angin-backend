package com.***REMOVED***.site.config;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;

@Configuration
public class DAOConfig {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DAOConfig.class);

    //user mongoDB
    @Value("${userMongoDB.host}")
    private String userMongoDBHost;
    @Value("${userMongoDB.port}")
    private int userMongoDBPort;
    @Value("${userMongoDB.userName}")
    private String userMongoDBUserName;
    @Value("${userMongoDB.authDB}")
    private String userMongoDBAuthDB;
    @Value("${userMongoDB.password}")
    private String userMongoDBPassword;

    //chat mongoDB
    @Value("${chatMongoDB.host}")
    private String chatMongoDBHost;
    @Value("${chatMongoDB.port}")
    private int chatMongoDBPort;
    @Value("${chatMongoDB.userName}")
    private String chatMongoDBUserName;
    @Value("${chatMongoDB.authDB}")
    private String chatMongoDBAuthDB;
    @Value("${chatMongoDB.password}")
    private String chatMongoDBPassword;

    //card mongoDB
    @Value("${cardMongoDB.host}")
    private String cardMongoDBHost;
    @Value("${cardMongoDB.port}")
    private int cardMongoDBPort;
    @Value("${cardMongoDB.userName}")
    private String cardMongoDBUserName;
    @Value("${cardMongoDB.authDB}")
    private String cardMongoDBAuthDB;
    @Value("${cardMongoDB.password}")
    private String cardMongoDBPassword;

    @Bean
    @Primary
    public MongoTemplate mongoTemplateForCard() {
        log.info("inject mongoTemplateForCard");
        MongoCredential mongoCredential = MongoCredential.createCredential(cardMongoDBUserName, cardMongoDBAuthDB,
                cardMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(cardMongoDBHost, cardMongoDBPort);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)),
                "back_end");
    }

    @Bean
    public MongoTemplate mongoTemplateForChat() {
        log.info("inject mongoTemplateForChat");
        MongoCredential mongoCredential = MongoCredential.createCredential(chatMongoDBUserName, chatMongoDBAuthDB,
                chatMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(chatMongoDBHost, chatMongoDBPort);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)), "chat");
    }

    @Bean
    public MongoTemplate mongoTemplateForUser() {
        log.info("inject mongoTemplateForUser");
        MongoCredential mongoCredential = MongoCredential.createCredential(userMongoDBUserName, userMongoDBAuthDB,
                userMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(userMongoDBHost, userMongoDBPort);
        return new MongoTemplate(new MongoClient(serverAddress, Collections.singletonList(mongoCredential)), "user");
    }

}