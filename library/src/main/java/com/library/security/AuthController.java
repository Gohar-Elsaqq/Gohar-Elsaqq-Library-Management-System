package com.library.security;

import com.library.response.SuccessResponse;
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
    public SuccessResponse<JwtTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        JwtTokenResponse jwtAuthResponse = authenticationServices.login(loginRequest.getUserName(), loginRequest.getPassword()).getDetails();

        return new SuccessResponse<>("Authentication successful", jwtAuthResponse);
    }

}