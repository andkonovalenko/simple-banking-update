package com.practice.bank.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {

    @NotBlank
    @Email(message = "Enter valid e-mail!")
    private String email;

    @NotBlank(message = "Enter password!")
    @Length(min = 4, max = 25, message = "Password must be between 3 and 30 characters")
    private String password;
}
