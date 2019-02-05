package by.iba.configuration;

import by.iba.configuration.database.mongo.MongoConfiguration;
import by.iba.configuration.freemarker.FreeMarkerConfiguration;
import by.iba.configuration.mail.MailConfiguration;
import by.iba.configuration.rest.template.RestTemplateConfiguration;
import by.iba.configuration.swagger.SwaggerConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Import({MongoConfiguration.class,
        RestTemplateConfiguration.class,
        SwaggerConfiguration.class,
        MailConfiguration.class,
        FreeMarkerConfiguration.class})
@PropertySource(value = {"classpath:application.properties",
        "classpath:application-dev.properties",
        "classpath:application-test.properties",
        "classpath:endpoint.properties",
        "classpath:smtp.properties"})
        public class ApplicationConfiguration { }