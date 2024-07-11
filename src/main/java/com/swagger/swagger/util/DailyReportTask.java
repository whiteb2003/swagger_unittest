package com.swagger.swagger.util;

import com.swagger.swagger.exception.AppException;
import com.swagger.swagger.service.ExceptionTrackingService;
import com.swagger.swagger.service.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class DailyReportTask {
    @Autowired
    private ExceptionTrackingService exceptionTrackingService;

    @Autowired
    private SendMail sendMail;

    @Scheduled(cron = "0 50 15 * * ?")
    public void sendDailyReport() {
        int exceptionCount = exceptionTrackingService.getExceptionCount();
        List<AppException> exceptions = exceptionTrackingService.getExceptions();
        LocalDateTime lastResetTime = exceptionTrackingService.getLastResetTime();

        StringBuilder content = new StringBuilder();
        content.append(String.format("Number of exceptions from %s to %s: %d<br/>",
                lastResetTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                exceptionCount));

        for (AppException ex : exceptions) {
            content.append(String.format("<div><strong>Message:</strong> %s<br/><strong>Stack:</strong> %s<br/><strong>Date:</strong> %s</div><br/>",
                    ex.getMessage(), ex.getStackTrace(), ex.getTime()));
        }

        try {
            sendMail.sendEmail2("hoanghaido2003@gmail.com", "Daily Exception Report", content.toString(), new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        exceptionTrackingService.resetExceptionCount();
    }
}