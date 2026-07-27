package com.bizsquare.bizsquare_backend.auth.dto;

public record AuthResponse(
        String token,
        String tokenType
) {}