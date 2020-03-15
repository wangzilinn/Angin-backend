package com.***REMOVED***.site;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class SiteApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Value("${env}")
    private String env;

    @Override
    public void run(String... args) {
        System.out.println(env);
    }

}
