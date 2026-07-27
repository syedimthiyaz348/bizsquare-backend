package com.bizsquare.bizsquare_backend.user.service;

import com.bizsquare.bizsquare_backend.user.dto.AssignRoleRequest;
import com.bizsquare.bizsquare_backend.user.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAllRoles();
    void assignRoleToStaff(AssignRoleRequest request, Integer ownerId);
}
