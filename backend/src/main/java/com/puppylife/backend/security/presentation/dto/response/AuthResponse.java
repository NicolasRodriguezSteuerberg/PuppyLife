package com.puppylife.backend.security.presentation.dto.response;

public record AuthResponse(
        String username,
        String msg,
        String jwt,
        boolean status
) {
}
