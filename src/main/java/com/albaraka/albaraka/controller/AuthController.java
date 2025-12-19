package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.model.dto.user.RegisterDTO;
import com.albaraka.albaraka.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterDTO dto,
            HttpServletRequest req
    ) {
        var result = service.register(dto);

        var body = Map.of(
                "date", LocalDateTime.now(),
                "status", 200,
                "message", "Votre Compte est cree avec success",
                "data", result,
                "path", req.getRequestURI()
        );

        return ResponseEntity.ok(body);
    }
}
