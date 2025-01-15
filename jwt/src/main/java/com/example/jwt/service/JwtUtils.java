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
//        String secretKey = Base64.getEncoder().encodeToString(keyBytes);
        String jwtSecrets = "DANGVANSYUUUSKAHSHSITKASJSNSALTASJSASS";
        String secretKey = Base64.getEncoder().encodeToString(jwtSecrets.getBytes());

        return secretKey;
    }


    private final String jwtSecret = jwtSecret();
//    private final String jwtSecret = "DANGVANSYUUUSKAHSHSITKASJSNSALTASJSASS";
    private final long accessToken = 30000;
    private final long refreshToken = 6000000;

    public String generateAccessToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessToken))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshToken))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public boolean isJwt(String token) {
        System.out.println("sercet:"+jwtSecret());
        try {
            if(token.startsWith("Bearer ")) {
                token = token.substring("Bearer ".length());
            }
            System.out.println("token:"+token);
            // Sử dụng parserBuilder để kiểm tra token
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
            return false; // Token hết hạn, trả về false
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false; // Trường hợp token không hợp lệ
    }

}
