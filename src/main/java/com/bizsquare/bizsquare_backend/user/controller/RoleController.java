package com.bizsquare.bizsquare_backend.user.controller;

import com.bizsquare.bizsquare_backend.security.CustomUserDetails;
import com.bizsquare.bizsquare_backend.user.dto.AssignRoleRequest;
import com.bizsquare.bizsquare_backend.user.dto.RoleResponse;
import com.bizsquare.bizsquare_backend.user.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignRoleToStaff(@Valid @RequestBody AssignRoleRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        roleService.assignRoleToStaff(request, userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
