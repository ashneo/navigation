package com.sdc.navigation.backend.config;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.sdc.navigation.backend.service.ServiceStationService;
import com.sdc.navigation.backend.repository.ServiceStationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by a.shloma on 13.04.2016.
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = ServiceStationRepository.class)
@ComponentScan(basePackageClasses = ServiceStationService.class)
public class TestMongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "tests";
    }

    @Override
    @Bean
    public MongoClient mongo() throws Exception {
        MongoClient client = new MongoClient("localhost");
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }
}
