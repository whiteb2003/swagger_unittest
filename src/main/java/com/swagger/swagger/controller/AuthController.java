package com.swagger.swagger.controller;

import java.util.regex.Pattern;

import com.swagger.swagger.entity.UserEntity;
import com.swagger.swagger.exception.UserException;
import com.swagger.swagger.jwt.JwtService;
import com.swagger.swagger.repository.UserRepository;
import com.swagger.swagger.service.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        UserEntity exist = userRepository.findByUsername(userDto.getUsername());
        if(exist==null){
            return ResponseEntity.status(400).body(new UserException(400,"LOGIN_ERROR","Wrong Username"));
        }else{
            try{
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getUsername() + userDto.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
                return ResponseEntity.status(200).body(new UserException(200,"LOGIN_SUCCESS",jwtService.generateToken(userDetails)));
            }catch (AuthenticationException ex){
                return ResponseEntity.status(400).body(new UserException(400,"LOGIN_ERROR","Wrong Password"));
            }
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        String passwordRegex = "^.{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        UserEntity userEntity = new UserEntity();
        UserEntity exist = userRepository.findByUsername(userDto.getUsername());
        if(exist!=null){
            return ResponseEntity.status(400).body(new UserException(400,"REGISTER_ERROR","Username was already taken!"));
        }else if (userDto.getUsername().isBlank()) {
            return ResponseEntity.status(400).body(new UserException(400,"REGISTER_ERROR","Username is required"));
        } else if (!pattern.matcher(userDto.getPassword()).matches()) {
            return ResponseEntity.status(400).body(new UserException(400,"REGISTER_ERROR","Password's length must larger or equal 8 characters"));
        }else {
            userEntity.setEnabled(true);
            userEntity.setPassword(encoder.encode(userDto.getUsername()+userDto.getPassword()));
            userEntity.setUsername(userDto.getUsername());
            userEntity.setVerificationCode(null);
            userEntity.setRoles(null);
            userRepository.save(userEntity);
            return ResponseEntity.status(200).body(new UserException(200,"REGISTER_SUCCESSFULLY","Register Successfully!"));
        }
    }
}
