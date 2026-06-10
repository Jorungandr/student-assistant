package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.LoginRequest;
import com.example.studentassistant.dto.auth.LoginResponse;
import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.user.UpdatePasswordRequest;
import com.example.studentassistant.dto.user.UpdateProfileRequest;
import com.example.studentassistant.dto.user.UserProfileResponse;

public interface AuthService {
    UserProfileResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserProfileResponse profile(Long userId);

    UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request);

    void updatePassword(Long userId, UpdatePasswordRequest request);
}

