package by.iba.configuration.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    private MongoClient getMongoClient() {
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        return new MongoClient(new ServerAddress(host, port), MongoCredential.createCredential(username, database, password.toCharArray()),
                mongoClientOptions);
    }

    private MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(getMongoClient(), database);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
