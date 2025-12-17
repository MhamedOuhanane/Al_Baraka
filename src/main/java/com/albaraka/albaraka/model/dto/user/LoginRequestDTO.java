package com.albaraka.albaraka.model.dto.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequestDTO {
    private String username;
    private String password;
    private String confirmPassword;
}
