package by.iba.configuration;

import by.iba.configuration.database.mongo.MongoConfiguration;
import by.iba.configuration.mail.MailConfiguration;
import by.iba.configuration.rest.template.RestTemplateConfiguration;
import by.iba.configuration.swagger.SwaggerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        MongoConfiguration.class,
        RestTemplateConfiguration.class, SwaggerConfiguration.class, MailConfiguration.class})
public class ApplicationConfiguration {

}
