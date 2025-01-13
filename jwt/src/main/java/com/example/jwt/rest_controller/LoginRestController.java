package com.example.jwt.rest_controller;

import com.example.jwt.dtos.request.LoginRequest;
import com.example.jwt.entity.RefreshToken;
import com.example.jwt.repository.IRefreshTokenRepository;
import com.example.jwt.service.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Duration;

@RestController

public class LoginRestController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if ("admin".equals(loginRequest.getUsername()) && "111".equals(loginRequest.getPassword())) {
            String accessToken = jwtUtils.generateAccessToken(loginRequest.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getUsername());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            RefreshToken refreshTokenDB = RefreshToken.builder()
                    .token(refreshToken)
                    .createdAt(now)
                    .userId(loginRequest.getUsername())
                    .build();
            refreshTokenRepository.save(refreshTokenDB);
            ResponseCookie responseCookie = ResponseCookie.from("token", accessToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("Lax") // Không dùng None ở localhost nếu không dùng HTTPS
                    .secure(false)   // Bỏ secure để cookie hoạt động trên HTTP
                    .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(accessToken);
        }
        return ResponseEntity.badRequest().body("fail!");
    }
}
