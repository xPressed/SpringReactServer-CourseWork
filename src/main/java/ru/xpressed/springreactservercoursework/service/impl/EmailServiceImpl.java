package ru.xpressed.springreactservercoursework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.xpressed.springreactservercoursework.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${mail.address}")
    private String  verifyAddress;

    @Override
    public void sendMessage(String address, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String msg = "<div style=\"text-align: center\">\n" +
                "<h2>Spring Table E-Mail Verification</h2>\n" +
                "<h4>Please, follow the next link!</h4>\n" +
                "<a href=\"http://" + verifyAddress + "/verify/" + body + "\">Click Me!</a>\n" +
                "</div>";

        helper.setText(msg, true);
        helper.setTo(address);
        helper.setSubject("Email Verification");

        javaMailSender.send(message);
    }
}
