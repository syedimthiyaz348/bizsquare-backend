package com.bizsquare.bizsquare_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank @Email String email,
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {
}