package by.iba.configuration;

import by.iba.configuration.database.mongo.MongoConfiguration;
import by.iba.configuration.freemarker.FreeMarkerConfiguration;
import by.iba.configuration.mail.MailConfiguration;
import by.iba.configuration.rest.template.RestTemplateConfiguration;
import by.iba.configuration.swagger.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

@Import({
        MongoConfiguration.class,
        RestTemplateConfiguration.class, SwaggerConfiguration.class, MailConfiguration.class, FreeMarkerConfiguration.class})
public class ApplicationConfiguration {

}
