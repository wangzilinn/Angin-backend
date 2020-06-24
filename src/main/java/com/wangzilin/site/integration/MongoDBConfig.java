package com.wangzilin.site.integration;


import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;

@Configuration
@Slf4j
public class MongoDBConfig {

    //blog mongoDB
    @Value("${blogMongoDB.host}")
    private String blogMongoDBHost;
    @Value("${blogMongoDB.port}")
    private int blogMongoDBPort;
    @Value("${blogMongoDB.userName}")
    private String blogMongoDBUserName;
    @Value("${blogMongoDB.authDB}")
    private String blogMongoDBAuthDB;
    @Value("${blogMongoDB.password}")
    private String blogMongoDBPassword;

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

    //file mongoDB
    @Value("${fileMongoDB.host}")
    private String fileMongoDBHost;
    @Value("${fileMongoDB.port}")
    private int fileMongoDBPort;
    @Value("${fileMongoDB.userName}")
    private String fileMongoDBUserName;
    @Value("${fileMongoDB.authDB}")
    private String fileMongoDBAuthDB;
    @Value("${fileMongoDB.password}")
    private String fileMongoDBPassword;

    @Bean
    @Primary
    public MongoTemplate mongoTemplateForCard() {
        log.info("inject mongoTemplateForCard");
        MongoCredential mongoCredential = MongoCredential.createCredential(cardMongoDBUserName, cardMongoDBAuthDB,
                cardMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(cardMongoDBHost, cardMongoDBPort);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyToClusterSettings(builder -> {
            builder.hosts(Collections.singletonList(serverAddress));
        }).credential(mongoCredential).build();
        return new MongoTemplate(MongoClients.create(mongoClientSettings), "back_end");
    }

    @Bean
    public MongoTemplate mongoTemplateForChat() {
        log.info("inject mongoTemplateForChat");
        MongoCredential mongoCredential = MongoCredential.createCredential(chatMongoDBUserName, chatMongoDBAuthDB,
                chatMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(chatMongoDBHost, chatMongoDBPort);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyToClusterSettings(builder -> {
            builder.hosts(Collections.singletonList(serverAddress));
        }).credential(mongoCredential).build();
        return new MongoTemplate(MongoClients.create(mongoClientSettings), "chat");
    }

    @Bean
    public MongoTemplate mongoTemplateForBlog() {
        log.info("inject mongoTemplateForBlog");
        MongoCredential mongoCredential = MongoCredential.createCredential(blogMongoDBUserName, blogMongoDBAuthDB,
                blogMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(blogMongoDBHost, blogMongoDBPort);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyToClusterSettings(builder -> {
            builder.hosts(Collections.singletonList(serverAddress));
        }).credential(mongoCredential).build();
        return new MongoTemplate(MongoClients.create(mongoClientSettings), "blog");
    }

    @Bean
    public MongoTemplate mongoTemplateForFile() {
        log.info("inject mongoTemplateForFile");
        MongoCredential mongoCredential = MongoCredential.createCredential(fileMongoDBUserName, fileMongoDBAuthDB,
                fileMongoDBPassword.toCharArray());
        ServerAddress serverAddress = new ServerAddress(fileMongoDBHost, fileMongoDBPort);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyToClusterSettings(builder -> {
            builder.hosts(Collections.singletonList(serverAddress));
        }).credential(mongoCredential).build();
        return new MongoTemplate(MongoClients.create(mongoClientSettings), "file");
    }

}
