package com.practice.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.bank.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @NotNull
    private Long id;
    @NotBlank
    private String userName;
    @NotBlank
    private String email;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setEmail(email);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
