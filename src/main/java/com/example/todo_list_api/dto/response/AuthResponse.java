package com.example.todo_list_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response setelah login berhasil")
public class AuthResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Type token", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Data user yang login")
    private UserResponse user;

    public AuthResponse(String accessToken, UserResponse user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
