package com.bix.upload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("uploadimageasync96@gmail.com");
        message.setTo(to);
        message.setSubject("User created");
        message.setText("User created in UploadImageAsync");
        javaMailSender.send(message);
    }
}