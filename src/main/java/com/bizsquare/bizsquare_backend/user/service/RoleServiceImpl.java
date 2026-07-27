package com.bizsquare.bizsquare_backend.user.service;

import com.bizsquare.bizsquare_backend.auth.dao.RoleDao;
import com.bizsquare.bizsquare_backend.auth.dao.UserDao;
import com.bizsquare.bizsquare_backend.auth.dao.UserRoleDao;
import com.bizsquare.bizsquare_backend.user.dto.AssignRoleRequest;
import com.bizsquare.bizsquare_backend.user.dto.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final UserDao userDao;
    private final UserRoleDao userRoleDao;

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public void assignRoleToStaff(AssignRoleRequest request, Integer ownerId) {
        userDao.findUserById(request.userId()).ifPresent(user -> {
            if (!user.ownerId().equals(ownerId)) {
                throw new AccessDeniedException("You can only assign roles to your own staff.");
            }
        });

        userRoleDao.assignRoleToUser(request.userId(), request.roleId());
    }
}
