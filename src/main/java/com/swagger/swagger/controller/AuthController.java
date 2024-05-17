package com.swagger.swagger.controller;

import java.util.regex.Pattern;

import com.swagger.swagger.entity.UserEntity;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.internationalization.MyLocalResolver;
import com.swagger.swagger.jwt.JwtService;
import com.swagger.swagger.repository.UserRepository;
import com.swagger.swagger.service.CustomUserDetail;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private MessageSource messageSource;
    @Autowired
    private MyLocalResolver myLocalResolver;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                            userDto.getUsername() + userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            return ResponseEntity.status(200)
                    .body(new UserException(200, "LOGIN_SUCCESS", jwtService.generateToken(userDetails)));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(400).body(new UserException(400, "LOGIN_ERROR", messageSource
                    .getMessage("Wrong_Username_or_Password", null,
                            myLocalResolver.resolveLocale(request))));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpDto signUpDto, HttpServletRequest request) {
        String passwordRegex = "^.{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        UserEntity userEntity = new UserEntity();
        UserEntity exist = userRepository.findByUsername(signUpDto.getUsername());
        if (exist != null) {
            return ResponseEntity.status(400)
                    .body(new UserException(400, "REGISTER_ERROR", messageSource
                            .getMessage("User_name_was_exist", null, myLocalResolver.resolveLocale(request))));
        } else if (signUpDto.getUsername() == null || signUpDto.getUsername().isBlank()) {
            return ResponseEntity.status(400).body(new UserException(400, "REGISTER_ERROR", messageSource
                    .getMessage("Username_is_required", null, myLocalResolver.resolveLocale(request))));
        } else if (signUpDto.getPassword() == null || !pattern.matcher(signUpDto.getPassword()).matches()) {
            return ResponseEntity.status(400).body(
                    new UserException(400, "REGISTER_ERROR", messageSource
                            .getMessage("Password's_length_must_larger_or_equal_8_characters", null,
                                    myLocalResolver.resolveLocale(request))));
        } else if (signUpDto.getConfirmPassword() == null || signUpDto.getConfirmPassword().isBlank()) {
            return ResponseEntity.status(400).body(
                    new UserException(400, "REGISTER_ERROR", messageSource
                            .getMessage("Confirm_password_require", null, myLocalResolver.resolveLocale(request))));
        } else if (!signUpDto.getConfirmPassword().equals(signUpDto.getPassword())) {
            return ResponseEntity.status(400).body(
                    new UserException(400, "REGISTER_ERROR", messageSource
                            .getMessage("Confirm_password_was_not_same_password", null,
                                    myLocalResolver.resolveLocale(request))));
        } else {
            userEntity.setEnabled(true);
            userEntity.setPassword(encoder.encode(signUpDto.getUsername() + signUpDto.getPassword()));
            userEntity.setUsername(signUpDto.getUsername());
            userEntity.setVerificationCode(null);
            userEntity.setRoles(null);
            userRepository.save(userEntity);
            return ResponseEntity.status(200)
                    .body(new UserException(200, "REGISTER_SUCCESSFULLY", messageSource
                            .getMessage("Register_Successfully", null, myLocalResolver.resolveLocale(request))));
        }
    }
}
