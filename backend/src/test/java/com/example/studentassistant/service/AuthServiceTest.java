package com.example.studentassistant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.auth.LoginRequest;
import com.example.studentassistant.dto.auth.LoginResponse;
import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.entity.User;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper.delete(new LambdaQueryWrapper<User>().likeRight(User::getUsername, "auth_test_"));
    }

    @Test
    void duplicateUsernameCannotRegister() {
        RegisterRequest request = registerRequest("auth_test_duplicate");
        authService.register(request);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("用户名已存在");
    }

    @Test
    void loginFailsWithWrongPassword() {
        authService.register(registerRequest("auth_test_wrong_password"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("auth_test_wrong_password");
        loginRequest.setPassword("bad-password");

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("用户名或密码错误");
    }

    @Test
    void loginSucceedsWithCorrectPassword() {
        authService.register(registerRequest("auth_test_success"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("auth_test_success");
        loginRequest.setPassword("password123");

        LoginResponse response = authService.login(loginRequest);

        assertThat(response.getToken()).isNotBlank();
        assertThat(response.getUser().getUsername()).isEqualTo("auth_test_success");
    }

    private RegisterRequest registerRequest(String username) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword("password123");
        request.setNickname("测试用户");
        request.setEmail(username + "@example.com");
        return request;
    }
}

