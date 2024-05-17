package com.swagger.swagger;

import com.swagger.swagger.controller.AuthController;
import com.swagger.swagger.dto.SignUpDto;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SignUpTest {
    BCryptPasswordEncoder encoder;
    UserException userException;

    public SignUpTest() {
        encoder = new BCryptPasswordEncoder();
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthController authController;
    @Autowired
    HttpServletRequest request;

    @Test
    public void registerWithExistUsername() {
        SignUpDto userDto = new SignUpDto("dang", "123456789", "123456789");
        userException = (UserException) authController.register(userDto, request).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Username was already taken!");
    }

    @Test
    public void registerWithEmptyUsername() {
        SignUpDto userDto = new SignUpDto("", "123456789", "123456789");
        userException = (UserException) authController.register(userDto, request).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Username is required");
    }

    @Test
    public void registerWithEmptyPassword() {
        SignUpDto userDto = new SignUpDto("lam", "", "god12345");
        userException = (UserException) authController.register(userDto, request).getBody();
        Assertions.assertThat(userException.getMessages())
                .isEqualTo("Password's length must larger or equal 8 characters");
    }

    @Test
    public void registerWithEmptyConfirmPassword() {
        SignUpDto userDto = new SignUpDto("lam", "god12345", "");
        userException = (UserException) authController.register(userDto, request).getBody();
        Assertions.assertThat(userException.getMessages())
                .isEqualTo("Confirm password require");
    }

    @Test
    public void registerWithInvalidConfirmPassword() {
        SignUpDto userDto = new SignUpDto("lam", "god12345", "god123456");
        userException = (UserException) authController.register(userDto, request).getBody();
        Assertions.assertThat(userException.getMessages())
                .isEqualTo("Confirm password was not same password");
    }

    @Test
    public void registerSucces() {
        SignUpDto userDto = new SignUpDto("lam", "abc1234567", "abc1234567");
        userException = (UserException) authController.register(userDto, request).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Register Successfully!");
    }
}
