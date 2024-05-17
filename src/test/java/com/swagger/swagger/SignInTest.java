package com.swagger.swagger;

import com.swagger.swagger.controller.AuthController;
import com.swagger.swagger.dto.UserDto;
import com.swagger.swagger.exception.UserException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignInTest {
    @Autowired
    AuthController authController;
    UserException userException;

    @Test
    public void loginWithFail() {
        UserDto userDto = new UserDto("", "123456789");
        userException = (UserException) authController.login(userDto).getBody();
        Assertions.assertThat(userException.getMessages()).isEqualTo("Wrong Username or Password");
    }

    @Test
    public void loginSuccessful() {
        UserDto userDto = new UserDto("dang", "dang1234");
        userException = (UserException) authController.login(userDto).getBody();
        Assertions.assertThat(userException.getErrorCode()).isEqualTo("LOGIN_SUCCESS");
    }
}
