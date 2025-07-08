package com.example.todo_list_api.dto.response;

import com.example.todo_list_api.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response data user")
public class UserResponse {

    @Schema(description = "ID unik user", example = "1")
    private Long id;

    @Schema(description = "Username user", example = "john_doe")
    private String username;

    @Schema(description = "Email user", example = "john@example.com")
    private String email;

    @Schema(description = "Nama depan user", example = "John")
    private String firstName;

    @Schema(description = "Nama belakang user", example = "Doe")
    private String lastName;

    @Schema(description = "Role user", example = "USER")
    private User.Role role;

    @Schema(description = "Status aktif user", example = "true")
    private Boolean enabled;

    @Schema(description = "Tanggal registrasi", example = "2023-07-08T10:30:00")
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getEnabled(),
                user.getCreatedAt());
    }
}
