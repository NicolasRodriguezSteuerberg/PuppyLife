package com.puppylife.backend.security.presentation.controller;

import com.puppylife.backend.security.presentation.dto.request.LoginRequest;
import com.puppylife.backend.security.presentation.dto.request.RegisterRequest;
import com.puppylife.backend.security.presentation.dto.response.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @PostMapping("sign-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return null;
    }

    @PostMapping("sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new IllegalArgumentException("Passwords must match");
        }

        return null;
    }
}
