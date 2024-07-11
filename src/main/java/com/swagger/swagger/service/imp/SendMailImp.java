package com.swagger.swagger.service.imp;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.swagger.swagger.service.SendMail;

@Service
public class SendMailImp implements SendMail {

    @Override
    public void sendEmail(String mail, String message, String stack, Date date) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("haihdhe170135@fpt.edu.vn", "megftjteypnebxdr");
            }
        });
        String mailcontent = "<p> Dear admin" + ",</p>";
        mailcontent += "<h3> The application have error server!" + "</h3>";
        mailcontent += "<div> Message" + message + "</div>";
        mailcontent += "<div> Stack" + stack + "</div>";
        mailcontent += "<div> Date" + date + "</div>";
        mailcontent += "<p>Thank you</p>";

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("haihdhe170135@fpt.edu.vn", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject("Check", "utf-8");
        msg.setContent(mailcontent, "text/html;charset=utf-8");
        Transport.send(msg);
    }

    @Override
    public void sendEmail2(String mail, String subject, String content, Date date) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("haihdhe170135@fpt.edu.vn", "megftjteypnebxdr");
            }
        });

        String mailContent = "<p> Dear admin,</p>";
        mailContent += content;
        mailContent += "<p>Thank you</p>";

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("haihdhe170135@fpt.edu.vn", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject(subject, "utf-8");
        msg.setContent(mailContent, "text/html;charset=utf-8");
        Transport.send(msg);
    }

}
