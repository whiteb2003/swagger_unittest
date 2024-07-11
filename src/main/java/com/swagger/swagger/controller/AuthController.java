package com.swagger.swagger.controller;

import com.swagger.swagger.dto.JwtTokenBlackList;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.jwt.JwtService;
import com.swagger.swagger.repository.UserRepository;
import com.swagger.swagger.response.BaseResponse;
import com.swagger.swagger.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.swagger.swagger.dto.SignUpDto;
import com.swagger.swagger.dto.UserDto;

@CrossOrigin("*")
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
    @Autowired
    private JwtTokenBlackList jwtTokenBlacklist;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody UserDto userDto,
                                               HttpServletRequest request) {
        return ResponseEntity.status(authService.auth(userDto, request).getStatusCode()==400 ? HttpStatus.BAD_REQUEST :HttpStatus.OK).
                body(
                        BaseResponse.builder()
                                .data(authService.auth(userDto, request))
                                .build()
                );
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody SignUpDto signUpDto,
            HttpServletRequest request) {
        return ResponseEntity.status(authService.register(signUpDto,request).getStatusCode() ==400? HttpStatus.BAD_REQUEST :HttpStatus.OK).body(
                BaseResponse.builder()
                        .data(authService.register(signUpDto,request))
                        .build()
        );
    }

    @GetMapping("/info")
    public BaseResponse<?> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return BaseResponse.builder()
                .data(username)
                .build();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = (String) authentication.getCredentials();  // Lấy JWT từ Authentication object
        jwtTokenBlacklist.blacklistToken(jwt);
        System.out.println("a" +  jwt);
        SecurityContextHolder.clearContext();  // Xóa SecurityContext

        return ResponseEntity.ok("Logout successful");
    }

}
