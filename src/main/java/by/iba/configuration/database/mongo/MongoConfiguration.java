package by.iba.configuration.database.mongo;

import by.iba.configuration.database.mongo.properties.MongoProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MongoConfiguration {

    @Autowired
    private MongoProperties mongoProperties;

    private MongoClient getMongoClient() {
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        return new MongoClient(new ServerAddress(mongoProperties.getHost(),mongoProperties.getPort()),
                MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getDatabase(), mongoProperties.getPassword().toCharArray()),
                mongoClientOptions);
    }

    private MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(getMongoClient(), mongoProperties.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
