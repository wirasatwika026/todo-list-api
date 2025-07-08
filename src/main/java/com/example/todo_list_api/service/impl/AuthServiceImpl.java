package com.example.todo_list_api.service.impl;

import com.example.todo_list_api.dto.request.LoginRequest;
import com.example.todo_list_api.dto.request.RegisterRequest;
import com.example.todo_list_api.dto.response.AuthResponse;
import com.example.todo_list_api.dto.response.UserResponse;
import com.example.todo_list_api.model.User;
import com.example.todo_list_api.repository.UserRepository;
import com.example.todo_list_api.security.JwtTokenProvider;
import com.example.todo_list_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((User) authentication.getPrincipal());

        User user = (User) authentication.getPrincipal();
        UserResponse userResponse = UserResponse.fromEntity(user);

        log.info("User {} logged in successfully", request.getUsername());
        return new AuthResponse(jwt, userResponse);
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        log.info("Registration attempt for username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(User.Role.USER);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        log.info("User {} registered successfully", request.getUsername());

        return UserResponse.fromEntity(savedUser);
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return UserResponse.fromEntity(user);
        }
        throw new RuntimeException("User not authenticated");
    }
}
