package com.bizsquare.bizsquare_backend.auth.service;

import com.bizsquare.bizsquare_backend.auth.dto.AddStaffRequest;
import com.bizsquare.bizsquare_backend.auth.dto.LoginRequest;
import com.bizsquare.bizsquare_backend.auth.dto.LoginResponse;
import com.bizsquare.bizsquare_backend.auth.dto.RegisterRequest;
import com.bizsquare.bizsquare_backend.auth.dto.ResetPasswordRequest;
import com.bizsquare.bizsquare_backend.auth.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    void registerOwner(RegisterRequest request);
    List<UserResponse> getAllUsers();
    LoginResponse login(LoginRequest request);
    void registerStaff(AddStaffRequest request, Integer ownerId);
    UserResponse getCurrentUser(Integer userId);
    List<UserResponse> getStaffByOwnerId(Integer ownerId);
    boolean resetPassword(ResetPasswordRequest request);
}
