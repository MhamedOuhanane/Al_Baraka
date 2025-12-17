package com.albaraka.albaraka.model.dto.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginDTO {
    private UUID uuid;
    private String fullName;
    private String role;
    private String accessToken;
}
