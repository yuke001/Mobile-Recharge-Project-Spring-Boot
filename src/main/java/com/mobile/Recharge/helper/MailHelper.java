package com.mobile.Recharge.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import com.mobile.Recharge.dto.User;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailHelper {

    @Autowired
    JavaMailSender sender;

    @Autowired
    TemplateEngine engine;

    public void sendEmail(User user) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("yuke00siva@gmail.com", "Mobile Recharge");

            Context context = new Context();
            context.setVariable("user", user);

            String text = engine.process("email-template.html", context);

            helper.setText(text, true);

            helper.setSubject("Recharge Successful");
            helper.setTo(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

}
