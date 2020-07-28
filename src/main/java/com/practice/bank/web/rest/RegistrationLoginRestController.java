package com.practice.bank.web.rest;

import com.practice.bank.dto.LoginRequestDto;
import com.practice.bank.dto.RegistrationRequestDto;
import com.practice.bank.service.AuthenticationService;
import com.practice.bank.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class RegistrationLoginRestController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public RegistrationLoginRestController(AuthenticationService authenticationService,
                                           UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authenticationService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/api/registration")
    public ResponseEntity registration(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {

        if (!registrationRequestDto.getPassword().equals(registrationRequestDto.getConfirmPassword()))
            throw new BadCredentialsException("Passwords do not match");

        if (!userService.isExist(registrationRequestDto.getEmail()))
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
