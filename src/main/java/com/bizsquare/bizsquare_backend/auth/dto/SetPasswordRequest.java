package com.bizsquare.bizsquare_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SetPasswordRequest(
        @NotBlank @Email String email,
        @NotBlank String newPassword
) {
}