package com.ll.sbb.Email;

import com.ll.sbb.DataNotFoundException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MailController {

    private final JavaMailSender mailSender;

    @GetMapping("/mailCheck")
    @ResponseBody
    public int processMailCheck(@RequestParam("email") String email) throws Exception {
        int mailKey = (int) ((Math.random() * (99999 - 10000 + 1)) + 10000);

        String from = "admin@ToolTool.com";//보내는 이 메일주소
        String to = email;
        String title = "회원가입시 필요한 인증번호 입니다.";
        String content = "[인증번호] " + mailKey + " 입니다. <br/> 인증번호 확인란에 기입해주십시오.";
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(title);
            mailHelper.setText(content, true);

            mailSender.send(mail);

        } catch (Exception e) {
            throw new DataNotFoundException("error");
        }
        return mailKey;
    }


    @PostMapping("/user/findPw/sendEmail")
    @ResponseBody
    public void sendEmail(@RequestParam("email") String userEmail, String userName) {
        char[] TempKey = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (TempKey.length * Math.random());
            str += TempKey[idx];

            String from = "admin@ToolTool.com";//보내는 이 메일주소
            String to = userEmail;
            String title = "임시 비밀번호입니다.";
            String content = userName + "님의" + "[임시 비밀번호] " + str + " 입니다. <br/> 접속한 후 비밀번호를 변경해주세요";
            try {
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

                mailHelper.setFrom(from);
                mailHelper.setTo(to);
                mailHelper.setSubject(title);
                mailHelper.setText(content, true);

                mailSender.send(mail);

            } catch (Exception e) {
                throw new DataNotFoundException("error");
            }
        }
    }

    @PostMapping("/user/findId/sendEmail")
    @ResponseBody
    public void sendEmailForId(@RequestParam("email") String email, String userName) {
        String from = "admin@ToolTool.com";//보내는 이 메일주소
        String to = email;
        String title = "아이디 찾기 결과입니다.";
        String content = "[아이디] " + userName + " 입니다. <br/>";
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(title);
            mailHelper.setText(content, true);

            mailSender.send(mail);

        } catch (Exception e) {
            throw new DataNotFoundException("error");
        }
    }
}