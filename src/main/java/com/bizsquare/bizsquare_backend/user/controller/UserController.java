package com.bizsquare.bizsquare_backend.user.controller;

import com.bizsquare.bizsquare_backend.auth.dto.UserResponse;
import com.bizsquare.bizsquare_backend.auth.service.AuthService;
import com.bizsquare.bizsquare_backend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(authService.getCurrentUser(userDetails.getId()));
    }

    @GetMapping("/staff")
    public ResponseEntity<List<UserResponse>> gettingStaffByOwnerId(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(authService.getStaffByOwnerId(userDetails.getId()));
    }
}
