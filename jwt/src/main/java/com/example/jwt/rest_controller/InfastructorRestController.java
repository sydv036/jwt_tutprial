package com.example.jwt.rest_controller;

import com.example.jwt.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfastructorRestController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshTokens) {
        System.out.printf(refreshTokens);
        if (refreshTokens.isEmpty() || !jwtUtils.isJwt(refreshTokens)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login lai");
        }
        String newAccessToken = jwtUtils.generateAccessToken("admin");
        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    }
}
