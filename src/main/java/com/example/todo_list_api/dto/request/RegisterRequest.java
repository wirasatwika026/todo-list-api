package com.example.todo_list_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request untuk registrasi user baru")
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username unik untuk login", example = "john_doe", required = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Schema(description = "Email address user", example = "john@example.com", required = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Password untuk login", example = "password123", required = true)
    private String password;

    @Schema(description = "Nama depan user", example = "John")
    private String firstName;

    @Schema(description = "Nama belakang user", example = "Doe")
    private String lastName;
}
