package com.library.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationServices authenticationServices;
    @PostMapping("/login")
    public JwtTokenResponse login(@Valid @RequestBody LoginRequest loginRequest){
        JwtTokenResponse jwtAuthResponse= authenticationServices.login(loginRequest.getUserName(),loginRequest.getPassword());
        return jwtAuthResponse;
    }
}