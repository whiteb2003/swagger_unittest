package com.swagger.swagger.service;

import com.swagger.swagger.dto.SignUpDto;
import com.swagger.swagger.dto.UserDto;
import com.swagger.swagger.exception.UserException;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    public UserException auth(UserDto userDto, HttpServletRequest request);

    public UserException register(SignUpDto userDto, HttpServletRequest request);
}
