package com.practice.bank.dto;

import com.practice.bank.domain.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationRequestDto {

    @NotBlank(message = "Enter your name!")
    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String userName;

    @NotBlank
    @Email(message = "Enter valid e-mail!")
    private String email;

    @NotBlank(message = "Enter password!")
    @Length(min = 4, max = 25, message = "Password must be between 3 and 25 characters")
    private String password;

    @NotBlank(message = "Confirm password!")
    @Length(min = 4, max = 25, message = "Password must be between 3 and 25 characters")
    private String confirmPassword;
}
