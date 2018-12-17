package by.iba.configuration.mail;

import by.iba.configuration.mail.constants.SenderConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailConfiguration {

    @Autowired
    private SenderConstants senderConstants;

    @Bean
    public JavaMailSender configureJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(senderConstants.getUsername());
        mailSender.setPassword(senderConstants.getPassword());
        mailSender.setHost(senderConstants.getHost());
        mailSender.setPort(senderConstants.getPort());
        mailSender.setProtocol(senderConstants.getProtocol());

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", senderConstants.getProtocol());
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
