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

    @Value("${mongodb.host}")
    private String host;
    @Value("${mongodb.port}")
    private int port;
    @Value("${mongodb.username}")
    private String username;
    @Value("${mongodb.password}")
    private String password;
    @Value("${mongodb.database}")
    private String database;

//    private MongoClient getMongoClient(@Value("${mongodb.host}") String host,
//                                       @Value("${mongodb.port}") int port,
//                                       @Value("${mongodb.username}") String username,
//                                       @Value("${mongodb.password}") String password,
//                                       @Value("${mongodb.database}") String database) {

    private MongoClient getMongoClient() {
//        this.host = host;
//        this.username = username;
//        this.password = password;
//        this.database = database;
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        return new MongoClient(new ServerAddress(host, port),
                MongoCredential.createCredential(username, database, password.toCharArray()), mongoClientOptions);
    }

    private MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(getMongoClient(), database);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
