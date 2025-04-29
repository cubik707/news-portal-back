package com.bsuir.newPortalBack.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public void sendSimpleMessage(String to, String subject, String text) {
    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text, false); // false = plain text
      helper.setFrom(fromEmail);

      mailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("Ошибка при отправке письма: " + e.getMessage());
    }
  }

  public void sendHtmlMessage(String to, String subject, String htmlBody) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlBody, true); // true — HTML
      helper.setFrom(fromEmail);

      mailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("Ошибка при отправке письма: " + e.getMessage());
    }
  }
}