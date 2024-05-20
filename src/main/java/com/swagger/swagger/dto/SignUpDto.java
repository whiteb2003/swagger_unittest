package com.swagger.swagger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    @NotBlank(message = "Username_is_required")
    private String username;

    @NotBlank(message = "Password_is_required")
    @Size(min = 8, message = "Password's_length_must_larger_or_equal_8_characters")
    private String password;

    @NotBlank(message = "Confirm_password_require")
    private String confirmPassword;
}
