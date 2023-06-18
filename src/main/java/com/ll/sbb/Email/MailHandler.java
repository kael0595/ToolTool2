package com.ll.sbb.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.activation.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailHandler {
    private Session mailSession;
    private MimeMessage message;

    public MailHandler() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.transport.protocol", "smtp");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");

        mailSession = Session.getInstance(mailProperties);
        message = new MimeMessage(mailSession);
    }

    public void setSubject(String subject) throws MessagingException {
        message.setSubject(subject);
    }

    public void setText(String textContent) throws MessagingException {
        message.setText(textContent);
    }

    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
        message.setFrom(new InternetAddress(email, name));
    }

    public void setTo(String email) throws MessagingException {
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
    }

//    public void addInline(String contentId, DataSource dataSource) throws MessagingException {
//        // 추가적인 설정이 필요한 경우 해당 부분을 구현해야 합니다.
//    }

    public void send() throws MessagingException {
        Transport.send(message);
    }
}