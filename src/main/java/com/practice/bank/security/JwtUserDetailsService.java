package com.practice.bank.security;

import com.practice.bank.domain.User;
import com.practice.bank.security.jwt.JwtUser;
import com.practice.bank.security.jwt.JwtUserFactory;
import com.practice.bank.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Autowired
    public JwtUserDetailsService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userService.findByEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + userEmail + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with userEmail: {} successfully loaded", userEmail);
        return jwtUser;
    }
}
