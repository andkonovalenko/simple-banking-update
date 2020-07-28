package com.practice.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.UserRole;
import com.practice.bank.domain.enumerations.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {

    @NotNull
    private Long id;
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotNull
    private Set<UserRole> userRoles;
    @NotNull
    private UserStatus userStatus;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setEmail(email);
        user.setUserRoles(userRoles);
        user.setUserStatus(userStatus);
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUserName(user.getUserName());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setUserRoles(user.getUserRoles());
        adminUserDto.setUserStatus(user.getUserStatus());
        return adminUserDto;
    }
}
