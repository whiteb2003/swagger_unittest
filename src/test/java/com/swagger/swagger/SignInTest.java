package com.swagger.swagger;

import com.swagger.swagger.controller.AuthController;
import com.swagger.swagger.dto.UserDto;
import com.swagger.swagger.exception.UserException;

import jakarta.servlet.http.HttpServletRequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignInTest {
    @Autowired
    AuthController authController;
    @Autowired
    UserException userException;

    @Test
    public void loginWithFail() {
        UserDto userDto = new UserDto("ad", "123456789");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        userException = (UserException) authController.login(userDto,
                mockRequest).getData();
        Assertions.assertThat(userException.getErrorCore()).isEqualTo("Wrong_Username_or_Password");

    }

    @Test
    public void loginSuccessful() {
        UserDto userDto = new UserDto("dang", "dang1234");
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        userException = (UserException) authController.login(userDto,
                mockRequest).getData();
        Assertions.assertThat(userException.getErrorCode()).isEqualTo("LOGIN_SUCCESS");
    }
}
