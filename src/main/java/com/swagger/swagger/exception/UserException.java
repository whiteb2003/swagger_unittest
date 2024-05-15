package com.swagger.swagger.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserException {
    private Integer statusCode;
    private String errorCode;
    private String messages;
}
