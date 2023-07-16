package com.example.follow.service;

import com.example.follow.except.BusinessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author LYF
 * @dec: 邮箱远征服务
 * @date 2023/7/16
 **/
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final Map<String, String> verificationCodes = new HashMap<>();


    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(String email) {
        try {
            String code = generateVerificationCode(6);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("1374329135@qq.com");
            message.setTo(email);
            message.setSubject("验证码");
            message.setText("尊敬的用户，你们的验证码是: " + code + ",请在五分钟内填写如下验证码。");
            mailSender.send(message);
            storeVerificationCode(email, code);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    public String generateVerificationCode(int length) {
        StringBuilder code = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0-9的随机数字
            code.append(digit);
        }

        return code.toString();
    }


    public void storeVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }

    public boolean verifyVerificationCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    public void clearVerificationCode(String email) {
        verificationCodes.remove(email);
    }
}
