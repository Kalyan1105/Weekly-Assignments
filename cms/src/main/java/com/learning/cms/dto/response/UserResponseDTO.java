package com.learning.cms.dto.response;

import com.learning.cms.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private String profilePicture;
    private LocalDateTime createdAt;
}
