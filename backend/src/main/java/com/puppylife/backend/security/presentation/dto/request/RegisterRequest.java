package com.puppylife.backend.security.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest (
    @NotBlank
    String username,
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "The email doesn't have a valid format")
    String email,
    String password,
    String confirmPassword,
    @Min(2)
    String firstName,
    @Min(2)
    String lastName
) {}
