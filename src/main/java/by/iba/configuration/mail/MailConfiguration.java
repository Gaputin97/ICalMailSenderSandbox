package by.iba.configuration.mail;

import by.iba.bussines.sender.constants.SenderConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailConfiguration {

    @Autowired
    private SenderConstants senderConstants;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String host = senderConstants.getHost();
        String protocol = senderConstants.getProtocol();
        int port = senderConstants.getPort();
        String username = senderConstants.getUsername();
        String password = senderConstants.getPassword();
        mailSender.setHost(host);
        mailSender.setProtocol(protocol);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol", senderConstants.getProtocol());
        properties.setProperty("mail.debug", "true");
        properties.put("mail.smtp.starttls.required", "true");
        return mailSender;
    }
}
