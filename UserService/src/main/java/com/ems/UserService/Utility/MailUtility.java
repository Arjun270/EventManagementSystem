package com.ems.UserService.Utility;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class MailUtility {

    private final JavaMailSender mailSender;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
    private static final SecureRandom RANDOM = new SecureRandom();

    public MailUtility(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public void sendPasswordResetEmail(String to, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Password Has Been Reset");
        message.setText("Your new password is: " + newPassword + "\nPlease change it after logging in.");
        mailSender.send(message);
    }
}