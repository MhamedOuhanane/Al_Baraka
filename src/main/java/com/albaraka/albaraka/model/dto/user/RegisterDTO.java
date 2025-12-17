package com.albaraka.albaraka.model.dto.user;

import com.albaraka.albaraka.model.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterDTO {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private UserStatus status;
    private String role;
}
