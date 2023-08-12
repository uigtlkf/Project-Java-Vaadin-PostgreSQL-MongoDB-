package com.example.application.data.nosql.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import com.mongodb.client.MongoClient;


@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUri;

    @Value("${spring.data.mongodb.database}")
    private String mongoDbDatabase;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClient mongoClient = MongoClients.create(mongoDbUri);
        MongoDatabaseFactory mongoDbFactory = new SimpleMongoClientDatabaseFactory(mongoClient, mongoDbDatabase);
        return new MongoTemplate(mongoDbFactory);
    }
}
