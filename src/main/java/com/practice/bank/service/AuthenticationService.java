package com.practice.bank.service;

import com.practice.bank.domain.User;
import com.practice.bank.domain.enumerations.UserRole;
import com.practice.bank.domain.enumerations.UserStatus;
import com.practice.bank.dto.LoginRequestDto;
import com.practice.bank.dto.RegistrationRequestDto;
import com.practice.bank.repository.UserRepository;
import com.practice.bank.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtTokenProvider jwtTokenProvider,
                                 UserServiceImpl userService,
                                 PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Map<Object, Object> login(LoginRequestDto loginRequestDto) {
        try {
            String userEmail = loginRequestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, loginRequestDto.getPassword()));
            User user = userService.findByEmail(userEmail);

            if (user == null)
                throw new UsernameNotFoundException("User with e-mail: " + userEmail + " not found");

            String token = jwtTokenProvider.createToken(userEmail, user.getUserRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("email", userEmail);
            response.put("token", token);
            return response;
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public void registration(RegistrationRequestDto registrationRequestDto) {

        Set<UserRole> userRole = new HashSet<>();
        userRole.add(UserRole.USER);

        User createdUser = new User();
        createdUser.setUserName(registrationRequestDto.getUserName());
        createdUser.setEmail(registrationRequestDto.getEmail());
        createdUser.setPassword(registrationRequestDto.getPassword());
        createdUser.setPassword(passwordEncoder.encode(createdUser.getPassword()));
        createdUser.setUserStatus(UserStatus.ACTIVE);
        createdUser.setUserRoles(userRole);

        userRepository.save(createdUser);
    }
}
