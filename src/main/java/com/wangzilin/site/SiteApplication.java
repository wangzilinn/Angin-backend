package com.wangzilin.site;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class SiteApplication implements CommandLineRunner {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SiteApplication.class);

    @Value("${env}")
    private String env;

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("running env: " + env);
    }

}
