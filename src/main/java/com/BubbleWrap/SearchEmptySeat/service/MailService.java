package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final String SENDER_NAME = "빈자리를 부탁해";
    private final String SENDER_EMAIL = System.getProperty("MAIL_USERNAME");


    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String tempPassword){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress(SENDER_EMAIL, SENDER_NAME, "UTF-8"));
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = getEmailTemplate(tempPassword);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(ErrorCode.EMAIL_SEND_ERROR.getCode() + e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(ErrorCode.EMAIL_SEND_ERROR.getCode() + e.getMessage());
        }
    }

    public String getEmailTemplate(String tempPassword) {
        return String.format("""
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>임시 비밀번호 안내</title>
        </head>
        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
            <div style="max-width: 600px; margin: 30px auto; background: #ffffff; padding: 20px;
                        border-radius: 10px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); text-align: center;">
        
                <div style="font-size: 24px; font-weight: bold; color: #007BFF;">빈자리를 부탁해</div>
        
                <div style="margin-top: 20px; font-size: 16px; color: #555;">
                    <p>안녕하세요,</p>
                    <p>요청하신 임시 비밀번호를 아래와 같이 보내드립니다.</p>
                    <p>로그인 후 반드시 비밀번호를 변경해 주세요.</p>
                    <div style="margin: 20px auto; display: inline-block; background: #007BFF;
                                color: #ffffff; font-size: 20px; padding: 10px 20px;
                                border-radius: 5px; font-weight: bold;">
                        %s
                    </div>
                </div>
        
                <div style="margin-top: 30px; font-size: 12px; color: #888;">
                    <p>이 이메일은 자동 발송되었으며, 회신하지 마세요.</p>
                </div>
            </div>
        </body>
        </html>
        """, tempPassword);
    }
}
