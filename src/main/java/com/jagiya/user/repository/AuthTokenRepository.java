package com.jagiya.user.repository;

import com.jagiya.user.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUsersTbUsersId(Long userId);
}
