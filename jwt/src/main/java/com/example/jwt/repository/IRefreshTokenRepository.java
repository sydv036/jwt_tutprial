package com.example.jwt.repository;

import com.example.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken getByUserId(String userId);
}
