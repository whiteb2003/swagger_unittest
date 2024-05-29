package com.swagger.swagger;

import com.swagger.swagger.controller.AuthController;
import com.swagger.swagger.dto.SignUpDto;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
    private Validator validator;

    @Test
    public void registerWithExistUsername() {
        SignUpDto userDto = new SignUpDto("dang", "123456789", "123456789");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        userException = (UserException) authController.register(userDto,
                mockRequest).getData();
        Assertions.assertThat(userException.getErrorCore()).isEqualTo("User_name_was_exist");
    }

    @Test
    public void registerWithEmptyUsername() {
        SignUpDto userDto = new SignUpDto("", "123456789", "123456789");
        Set<ConstraintViolation<SignUpDto>> violations = validator.validate(userDto);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("Username_is_required");
    }

    @Test
    public void registerWithEmptyPassword() {
        SignUpDto userDto = new SignUpDto("lam", "", "god12345");
        Set<ConstraintViolation<SignUpDto>> violations = validator.validate(userDto);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Password_is_required");
    }

    @Test
    public void registerWithWrongLengthPassword() {
        SignUpDto userDto = new SignUpDto("lam", "god123", "god12345");
        Set<ConstraintViolation<SignUpDto>> violations = validator.validate(userDto);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Password's_length_must_larger_or_equal_8_characters");
    }

    @Test
    public void registerWithEmptyConfirmPassword() {
        SignUpDto userDto = new SignUpDto("lam", "god12345", "");
        Set<ConstraintViolation<SignUpDto>> violations = validator.validate(userDto);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Confirm_password_require");
    }

    @Test
    public void registerWithInvalidConfirmPassword() {
        SignUpDto userDto = new SignUpDto("decao", "god12345", "god123456");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        userException = (UserException) authController.register(userDto,
                mockRequest).getData();
        Assertions.assertThat(userException.getErrorCore())
                .isEqualTo("Confirm_password_was_not_same_password");
    }

    @Test
    public void registerSucces() {
        SignUpDto userDto = new SignUpDto("decao", "abc1234567", "abc1234567");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        userException = (UserException) authController.register(userDto,
                mockRequest).getData();
        Assertions.assertThat(userException.getErrorCore()).isEqualTo("Register_Successfully");
    }
}
