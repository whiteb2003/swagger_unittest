package com.swagger.swagger.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.swagger.swagger.entity.ErrorLog;
import com.swagger.swagger.internationalization.MyLocalResolver;
import com.swagger.swagger.repository.ErrorLogRepository;
import com.swagger.swagger.service.SendMail;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    ErrorLogRepository errorLogRepository;
    @Autowired
    SendMail sendMail;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MyLocalResolver myLocalResolver;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppException> globleExcpetionHandler(Exception ex, WebRequest request)
            throws MessagingException {
        AppException errorDetail = new AppException(ex.getMessage(), Arrays.toString(ex.getStackTrace()),
                new Date());
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorMessage(errorDetail.getMessage());
        errorLog.setErrorStackTrace(errorDetail.getStackTrace());
        errorLog.setTimestamp(errorDetail.getTime());
        errorLogRepository.save(errorLog);
        sendMail.sendEmail("hoanghaido2003@gmail.com");
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<UserException>> customValidationErrorHanding(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<UserException> errorDetails = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            // String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            UserException error = new UserException(400, "REGISTER_ERROR", message, messageSource
                    .getMessage(message, null,
                            myLocalResolver.resolveLocale(request)));
            errorDetails.add(error);
        }
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
