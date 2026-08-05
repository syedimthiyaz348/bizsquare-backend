package com.bizsquare.bizsquare_backend.auth.service;

import com.bizsquare.bizsquare_backend.auth.dao.RoleDao;
import com.bizsquare.bizsquare_backend.auth.dao.UserDao;
import com.bizsquare.bizsquare_backend.auth.dao.UserRoleDao;
import com.bizsquare.bizsquare_backend.auth.dto.*;
import com.bizsquare.bizsquare_backend.exception.EmailNotFoundException;
import com.bizsquare.bizsquare_backend.exception.InvalidPasswordException;
import com.bizsquare.bizsquare_backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void registerOwner(RegisterRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        Integer userId = userDao.createUser(request.name(), request.email(), hashedPassword, true);
        Integer ownerRoleId = roleDao.findRoleIdByName("OWNER");

        userRoleDao.assignRoleToUser(userId, ownerRoleId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails, request.rememberMe());
        return new LoginResponse(token);
    }

    @Override
    @Transactional
    public void registerStaff(AddStaffRequest request, Integer ownerId) {
        String hashedPassword = passwordEncoder.encode(request.password());

        Integer staffId = userDao.createStaff(request.name(), request.email(), hashedPassword, true, ownerId);
        Integer staffRoleId = roleDao.findRoleIdByName("STAFF");

        userRoleDao.assignRoleToUser(staffId, staffRoleId);
    }

    @Override
    public UserResponse getCurrentUser(Integer userId) {
        return userDao.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
    }

    @Override
    public List<UserResponse> getStaffByOwnerId(Integer ownerId){
        return userDao.findStaffByOwnerId(ownerId);
    }
    
    @Override
    public boolean resetPassword(ResetPasswordRequest request){
        return userDao.findByEmail(request.email())
                .map(user -> {
                    if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
                        throw new InvalidPasswordException("Your old password is not matched");
                    }
                    String hashedPassword = passwordEncoder.encode(request.newPassword());
                    return userDao.resetPassword(request.email(), hashedPassword);
                })
                .orElseThrow(() -> new EmailNotFoundException("Email is not matched"));
    }
}
