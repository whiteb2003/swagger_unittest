package com.swagger.swagger.config;

import com.swagger.swagger.dto.JwtTokenBlackList;
import com.swagger.swagger.jwt.JwtFilter;
import com.swagger.swagger.jwt.JwtService;
import com.swagger.swagger.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https.csrf(csrf -> csrf.disable());
        https.authorizeHttpRequests(request -> request
                .requestMatchers("/*").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/signup").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/logs/page/**").permitAll()
                .requestMatchers("/logs/pageElement/**").permitAll()
                .requestMatchers("/download/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs").permitAll()
                .requestMatchers("/v3/api-docs/swagger-config").permitAll()
                .requestMatchers("/logs").authenticated()
                .anyRequest().authenticated());
        https.sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        https.authenticationProvider(authenticationProvider());

        https.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return https.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
