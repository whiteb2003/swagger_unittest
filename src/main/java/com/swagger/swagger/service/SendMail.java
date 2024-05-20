package com.swagger.swagger.service;

import javax.mail.MessagingException;

public interface SendMail {
    void sendEmail(String mail) throws MessagingException;
}
