package ru.xpressed.springreactservercoursework.service;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMessage(String address, String body) throws MessagingException;
}
