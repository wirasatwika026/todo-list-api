package com.example.todo_list_api.service;

import com.example.todo_list_api.dto.request.LoginRequest;
import com.example.todo_list_api.dto.request.RegisterRequest;
import com.example.todo_list_api.dto.response.AuthResponse;
import com.example.todo_list_api.dto.response.UserResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    UserResponse register(RegisterRequest request);

    UserResponse getCurrentUser();
}
