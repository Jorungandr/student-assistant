package com.example.studentassistant.dto.auth;

import com.example.studentassistant.dto.user.UserProfileResponse;

public class LoginResponse {
    private String token;
    private UserProfileResponse user;

    public LoginResponse() {
    }

    public LoginResponse(String token, UserProfileResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserProfileResponse getUser() {
        return user;
    }

    public void setUser(UserProfileResponse user) {
        this.user = user;
    }
}

