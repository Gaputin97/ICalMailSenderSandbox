package by.iba.bussines.sender.service.v1;

import by.iba.bussines.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.bussines.sender.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SenderServiceImpl implements SenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    EnrollmentServiceImpl enrollmentService;

    public void sendMeetingToUser(String meetingId, String userEmail) {
        //MimeMessage message;
        //javaMailSender.send(message);

    }
}
