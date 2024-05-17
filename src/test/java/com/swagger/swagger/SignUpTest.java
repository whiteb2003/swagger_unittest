package com.swagger.swagger;

import com.swagger.swagger.controller.AuthController;
import com.swagger.swagger.dto.UserDto;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.repository.UserRepository;
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

    @Test
    public void registerWithExistUsername() {
        UserDto userDto = new UserDto("dang", "123456789");
        userException = (UserException) authController.register(userDto).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Username was already taken!");
    }

    @Test
    public void registerWithEmptyUsername() {
        UserDto userDto = new UserDto("", "123456789");
        userException = (UserException) authController.register(userDto).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Username is required");
    }

    @Test
    public void registerWithInvalidPassword() {
        UserDto userDto = new UserDto("god", "god123");
        userException = (UserException) authController.register(userDto).getBody();
        Assertions.assertThat(userException.getMessages())
                .isEqualTo("Password's length must larger or equal 8 characters");
    }

    @Test
    public void registerSucces() {
        UserDto userDto = new UserDto("abc", "abc1234567");
        userException = (UserException) authController.register(userDto).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Register Successfully!");
    }
}
