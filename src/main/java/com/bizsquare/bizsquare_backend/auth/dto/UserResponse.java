package com.bizsquare.bizsquare_backend.auth.dto;

public record UserResponse(Integer id, String name, String email, Integer ownerId) {
}
