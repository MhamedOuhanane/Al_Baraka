package com.albaraka.albaraka.controller;

import com.albaraka.albaraka.security.user.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @GetMapping("/operations")
    public ResponseEntity<?> showsOperation(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok("{}");
    }
}
