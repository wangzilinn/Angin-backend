package com.wangzilin.site;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@Slf4j
public class SiteApplication implements CommandLineRunner {

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
