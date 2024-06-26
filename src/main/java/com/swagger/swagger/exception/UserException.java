package com.swagger.swagger.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class UserException {
    private Integer statusCode;
    private String errorCode;
    private String errorCore;
    private String messages;
}
