package by.iba.configuration.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoConfiguration {

    @Profile("dev")
    @Bean
    public MongoTemplate devMongoTemplate(@Value("${mongodb.host}") String host,
                                          @Value("${mongodb.port}") int port,
                                          @Value("${mongodb.username}") String username,
                                          @Value("${mongodb.password}") String password,
                                          @Value("${mongodb.database}") String database) {
        System.out.println("Dev mongo");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoClient mongoClient = new MongoClient(serverAddress, MongoCredential.createCredential(username, database, password.toCharArray()), mongoClientOptions);
        return new MongoTemplate(mongoClient, database);
    }

    @Profile("test")
    @Bean
    public MongoTemplate testMongoTemplate(@Value("${mongodb.host}") String host,
                                           @Value("${mongodb.port}") int port,
                                           @Value("${mongodb.username}") String username,
                                           @Value("${mongodb.password}") String password,
                                           @Value("${mongodb.database}") String database) {
        System.out.println("Test mongo");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoClient mongoClient = new MongoClient(serverAddress, MongoCredential.createCredential(username, database, password.toCharArray()), mongoClientOptions);
        return new MongoTemplate(mongoClient, database);
    }
}
