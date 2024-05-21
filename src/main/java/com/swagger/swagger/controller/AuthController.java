package com.swagger.swagger.controller;

import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.jwt.JwtService;
import com.swagger.swagger.repository.UserRepository;
import com.swagger.swagger.response.BaseResponse;
import com.swagger.swagger.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.swagger.dto.SignUpDto;
import com.swagger.swagger.dto.UserDto;

@RestController
public class AuthController {
        BCryptPasswordEncoder encoder;

        public AuthController() {
                encoder = new BCryptPasswordEncoder();
        }

        @Autowired
        UserRepository userRepository;
        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        JwtService jwtService;
        @Autowired
        private AuthService authService;

        @PostMapping("/login")
        public BaseResponse<UserException> login(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
                return BaseResponse.<UserException>builder()
                                .data(authService.auth(userDto, request))
                                .build();
        }

        @PostMapping("/signup")
        public BaseResponse<UserException> register(@Valid @RequestBody SignUpDto signUpDto,
                        HttpServletRequest request) {
                return BaseResponse.<UserException>builder()
                                .data(authService.register(signUpDto, request))
                                .build();
        }
}
