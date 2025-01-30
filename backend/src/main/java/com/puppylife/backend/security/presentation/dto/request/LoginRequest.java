package com.puppylife.backend.security.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    String usernameOrEmail;
    @NotNull
    String password;
}
