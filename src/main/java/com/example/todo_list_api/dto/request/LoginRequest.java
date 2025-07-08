package com.example.todo_list_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request untuk login user")
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username atau email untuk login", example = "john_doe", required = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password user", example = "password123", required = true)
    private String password;
}
