package com.example.todo_list_api.controller;

import com.example.todo_list_api.dto.request.LoginRequest;
import com.example.todo_list_api.dto.request.RegisterRequest;
import com.example.todo_list_api.dto.response.ApiResponse;
import com.example.todo_list_api.dto.response.AuthResponse;
import com.example.todo_list_api.dto.response.UserResponse;
import com.example.todo_list_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "API untuk autentikasi dan manajemen user")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrasi user baru", description = "Mendaftarkan user baru ke sistem")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User berhasil didaftarkan"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Data registrasi tidak valid"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Username atau email sudah digunakan")
    })
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody @Parameter(description = "Data registrasi user") RegisterRequest request) {

        log.info("User registration attempt for username: {}", request.getUsername());
        UserResponse userResponse = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", userResponse));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Melakukan autentikasi user dan mendapatkan JWT token")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login berhasil"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Username atau password salah"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Data login tidak valid")
    })
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody @Parameter(description = "Data login user") LoginRequest request) {

        log.info("Login attempt for user: {}", request.getUsername());
        AuthResponse authResponse = authService.login(request);

        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Mendapatkan informasi user yang sedang login")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Data user berhasil diambil"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "User tidak terautentikasi")
    })
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        UserResponse userResponse = authService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }
}
