package com.swagger.swagger.service;

import java.util.Date;

import javax.mail.MessagingException;

public interface SendMail {
    void sendEmail(String mail, String message, String stack, Date date) throws MessagingException;
    void sendEmail2(String mail, String subject, String content, Date date) throws MessagingException;

}
