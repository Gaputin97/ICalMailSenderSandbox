package by.iba.bussines.sender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    @Autowired
    private JavaMailSender javaMailSender;
}
