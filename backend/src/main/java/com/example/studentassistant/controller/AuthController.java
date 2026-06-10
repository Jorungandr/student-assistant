package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.auth.LoginRequest;
import com.example.studentassistant.dto.auth.LoginResponse;
import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.user.UpdatePasswordRequest;
import com.example.studentassistant.dto.user.UpdateProfileRequest;
import com.example.studentassistant.dto.user.UserProfileResponse;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<UserProfileResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> profile() {
        return ApiResponse.success(authService.profile(SecurityUtils.currentUserId()));
    }

    @PutMapping("/profile")
    public ApiResponse<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ApiResponse.success(authService.updateProfile(SecurityUtils.currentUserId(), request));
    }

    @PutMapping("/password")
    public ApiResponse<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(SecurityUtils.currentUserId(), request);
        return ApiResponse.success();
    }
}

