package com.example.Live.stream.service.impl;

import com.example.Live.stream.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPendingMail(String to, String name, String event) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject("Booking Request Received");

        mail.setText(
                "Hello " + name + ",\n\n" +
                        "Your booking request for event: " + event + " has been received.\n\n" +
                        "Status: PENDING ADMIN CONFIRMATION.\n\n" +
                        "Admin will review your booking soon.\n\n" +
                        "Thank you."
        );

        mailSender.send(mail);
    }

    @Override
    public void sendConfirmedMail(String to, String name, String event) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject("Booking Confirmed");

        mail.setText(
                "Hello " + name + ",\n\n" +
                        "Your booking for event: " + event + " has been CONFIRMED.\n\n" +
                        "Thank you."
        );

        mailSender.send(mail);
    }
}