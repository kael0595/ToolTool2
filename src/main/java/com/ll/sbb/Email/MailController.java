package com.ll.sbb.Email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MailController {

    private final JavaMailSender mailSender;

    @PostMapping(value = "/mailCheck")
    @ResponseBody
    public String processMailCheck(@RequestParam("sm_email") String sm_email) throws Exception {
        int serti = (int) ((Math.random() * (99999 - 10000 + 1)) + 10000);

        String from = "ants7021@naver.com";//보내는 이 메일주소
        String to = "changgyu549@gmail.com";
        String title = "회원가입시 필요한 인증번호 입니다.";
        String content = "[인증번호] " + serti + " 입니다. <br/> 인증번호 확인란에 기입해주십시오.";
        String num = "";
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(title);
            mailHelper.setText(content, true);

            mailSender.send(mail);
            num = Integer.toString(serti);

        } catch (Exception e) {
            num = "error";
        }
        return num;
    }

    @GetMapping("/mailCheck")
    public String showMailCheckPage() {
        return "mailCheck";
    }
}
