package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.auth.LoginRequest;
import com.example.studentassistant.dto.auth.LoginResponse;
import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.user.UpdatePasswordRequest;
import com.example.studentassistant.dto.user.UpdateProfileRequest;
import com.example.studentassistant.dto.user.UserProfileResponse;
import com.example.studentassistant.entity.User;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.UserMapper;
import com.example.studentassistant.security.JwtUtil;
import com.example.studentassistant.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public UserProfileResponse register(RegisterRequest request) {
        if (findByUsername(request.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setStatus(1);
        userMapper.insert(user);
        return UserProfileResponse.from(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return new LoginResponse(token, UserProfileResponse.from(user));
    }

    @Override
    public UserProfileResponse profile(Long userId) {
        return UserProfileResponse.from(requireUser(userId));
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = requireUser(userId);
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        user.setGender(request.getGender());
        userMapper.updateById(user);
        return UserProfileResponse.from(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = requireUser(userId);
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userMapper.updateById(user);
    }

    private User findByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .last("LIMIT 1"));
    }

    private User requireUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }
}

