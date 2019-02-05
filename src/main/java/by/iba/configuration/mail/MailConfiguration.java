package by.iba.configuration.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
public class MailConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender(@Value("${mail.host}") String host,
                                            @Value("${mail.port}") int port,
                                            @Value("${mail.username}")String username,
                                            @Value("${mail.password}")String password) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
