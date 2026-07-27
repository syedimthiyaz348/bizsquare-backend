package com.bizsquare.bizsquare_backend.auth.controller;

import com.bizsquare.bizsquare_backend.auth.dto.AddStaffRequest;
import com.bizsquare.bizsquare_backend.auth.dto.LoginRequest;
import com.bizsquare.bizsquare_backend.auth.dto.LoginResponse;
import com.bizsquare.bizsquare_backend.auth.dto.RegisterRequest;
import com.bizsquare.bizsquare_backend.auth.dto.UserResponse;
import com.bizsquare.bizsquare_backend.auth.service.AuthService;
import com.bizsquare.bizsquare_backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registerOwner")
    public ResponseEntity<String> registerOwner(@Valid @RequestBody RegisterRequest request) {
        authService.registerOwner(request);
        return ResponseEntity.ok("Owner registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/registerStaff")
    public ResponseEntity<String> registerStaff(@Valid @RequestBody AddStaffRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.registerStaff(request, userDetails.getId());
        return ResponseEntity.ok("Staff registered successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> gettingUsers(){
        return ResponseEntity.ok(authService.getAllUsers());
    }
}
