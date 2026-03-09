package com.example.Live.stream.service;

public interface MailService {

    void sendPendingMail(String to, String name, String event);

    void sendConfirmedMail(String to, String name, String event);
}