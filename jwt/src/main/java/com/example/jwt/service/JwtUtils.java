package com.example.jwt.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtils {
    public String jwtSecret() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(keyBytes);
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.printf(secretKey);
        return secretKey;
    }


    private final String jwtSecret = jwtSecret();
//    private final String jwtSecret = "DANGVANSYUUUSKAHSHSITKASJSNSALTASJSASS";
    private final long accessToken = 3600000;
    private final long refreshToken = 3600000;

    public String generateAccessToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessToken))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    //    public String generateRefreshToken(String userName) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(userName)
//                .getBody()
//                .getSubject();
//    }
    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshToken))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public boolean isJwt(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }
}
