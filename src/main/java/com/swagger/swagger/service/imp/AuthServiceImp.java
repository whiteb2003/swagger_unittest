package com.swagger.swagger.service.imp;

import com.swagger.swagger.entity.Token;
import com.swagger.swagger.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.swagger.swagger.dto.SignUpDto;
import com.swagger.swagger.dto.UserDto;
import com.swagger.swagger.entity.UserEntity;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.internationalization.MyLocalResolver;
import com.swagger.swagger.jwt.JwtService;
import com.swagger.swagger.repository.UserRepository;
import com.swagger.swagger.service.AuthService;
import com.swagger.swagger.service.CustomUserDetail;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MyLocalResolver myLocalResolver;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    @Override
    public UserException auth(UserDto userDto, HttpServletRequest request) {
//        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                            userDto.getUsername() + userDto.getPassword()));
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
            String token = jwtService.generateToken(userDetails);
            

            return new UserException(200, "LOGIN_SUCCESS", "Register_Successfully",
                    token);
//        } catch (AuthenticationException ex) {
//            return new UserException(400, "LOGIN_ERROR",
//                    "Wrong_Username_or_Password", messageSource
//                            .getMessage("Wrong_Username_or_Password", null,
//                                    myLocalResolver.resolveLocale(request)));
//        }
    }
    private void revokeAllTokenByUser(UserEntity user) {
        List<Token> validTokens = tokenRepository.findByUserId(Long.valueOf(user.getId()));
        if(validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });
        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, UserEntity user) {
        Token token = new Token();
        token.setToken(accessToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
    @Override
    public UserException register(SignUpDto signUpDto, HttpServletRequest request) {
        UserEntity exist = userRepository.findByUsername(signUpDto.getUsername());
        if (exist != null) {
            return new UserException(400, "REGISTER_ERROR", "User_name_was_exist",
                    messageSource
                            .getMessage("User_name_was_exist", null,
                                    myLocalResolver.resolveLocale(
                                            request)));
        } else if (!signUpDto.getConfirmPassword().equals(signUpDto.getPassword())) {
            return new UserException(400, "REGISTER_ERROR",
                    "Confirm_password_was_not_same_password", messageSource
                            .getMessage("Confirm_password_was_not_same_password",
                                    null,
                                    myLocalResolver.resolveLocale(
                                            request)));
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setPassword(encoder.encode(signUpDto.getUsername() +
                    signUpDto.getPassword()));
            userEntity.setUsername(signUpDto.getUsername());
            userRepository.save(userEntity);
            return new UserException(200, "REGISTER_SUCCESSFULLY",
                    "Register_Successfully",
                    messageSource.getMessage("Register_Successfully", null,
                            myLocalResolver.resolveLocale(
                                    request)));
        }
    }

}
