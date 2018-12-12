package by.iba.configuration;

import by.iba.configuration.database.mongo.MongoConfiguration;
import by.iba.configuration.rest.template.RestTemplateConfiguration;
import by.iba.configuration.swagger.SwaggerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoConfiguration.class, RestTemplateConfiguration.class, SwaggerConfiguration.class})
public class ApplicationConfiguration {
    private MongoConfiguration mongoConfiguration;
    private RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    public ApplicationConfiguration(MongoConfiguration mongoConfiguration, RestTemplateConfiguration restTemplateConfiguration) {
        this.mongoConfiguration = mongoConfiguration;
        this.restTemplateConfiguration = restTemplateConfiguration;
    }
}
