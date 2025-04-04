package money_changer.money_changer_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toSend, String subject, String body) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toSend);
        email.setSubject(subject);
        email.setText(body);
        javaMailSender.send(email);
    }

}
