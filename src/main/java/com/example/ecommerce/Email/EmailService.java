package com.example.ecommerce.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements EmailServiceInterface{
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    public String sendEmail(EmailModel emailModel)
    {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailModel.recipient);
            mailMessage.setText(emailModel.message);
            mailMessage.setSubject(emailModel.subject);
            javaMailSender.send(mailMessage);
            return new String("Mail send Successfully");

        } catch (Exception e) {
            // TODO: handle exception
            return e.getMessage();
        }
    }
}
