//package com.ll.sbb.Email;
//
//import jakarta.mail.Message;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import java.util.UUID;
//
//@RequiredArgsConstructor
//public class mailService {
//
//    private final JavaMailSender javaMailSender;
//
//    private MimeMessage createMessage(String code, String email) throws Exception {
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        message.addRecipients(Message.RecipientType.TO, email);
//        message.setSubject("오늘의 집 모의외주 프로젝트 인증 번호입니다.");
//        message.setText("이메일 인증코드: " + code);
//
//        message.setFrom(new InternetAddress(Secret.RECIPIENT));
//
//        return message;
//    }
//
//    public void sendMail(String code, String email) throws Exception {
//        try {
//            MimeMessage mimeMessage = createMessage(code, email);
//            javaMailSender.send(mimeMessage);
//        } catch (MailException mailException) {
//            mailException.printStackTrace();
//            throw new IllegalAccessException();
//        }
//    }
//
//    public long sendCertificationMail(String email) throws BaseException {
//        if (userProvider.checkEmail(email) == 1) {
//            throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);
//        }
//        try {
//            String code = UUID.randomUUID().toString().substring(0, 6);
//            sendMail(code, email);
//
//            return mailDao.createVerificationCode(code, email);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//    }
//
//}