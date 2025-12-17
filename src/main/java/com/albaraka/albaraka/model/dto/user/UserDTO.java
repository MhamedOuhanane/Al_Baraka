package com.albaraka.albaraka.model.dto.user;

import com.albaraka.albaraka.model.entity.Account;
import com.albaraka.albaraka.model.entity.Role;
import com.albaraka.albaraka.model.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTO {
    private UUID uuid;
    private String fullName;
    private String email;
    private UserStatus status;
    private LocalDateTime createdAt;
    private String role;
}
