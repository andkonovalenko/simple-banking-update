package com.practice.bank.security.jwt;

import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.UserRole;
import com.practice.bank.domain.enumerations.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.addAll(user.getUserRoles());

        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthority(userRoles),
                user.getUserStatus().equals(UserStatus.ACTIVE),
                user.getBankAccounts());
    }

    private static Set<GrantedAuthority> mapToGrantedAuthority(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole ->
                        new SimpleGrantedAuthority(userRole.name())
                ).collect(Collectors.toSet());
    }
}
