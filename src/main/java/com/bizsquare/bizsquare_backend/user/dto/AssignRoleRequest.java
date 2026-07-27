package com.bizsquare.bizsquare_backend.user.dto;

import jakarta.validation.constraints.NotNull;

public record AssignRoleRequest(
        @NotNull Integer userId,
        @NotNull Integer roleId
) {
}
