package com.jagiya.login.repository;

import com.jagiya.login.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUsersTbUsersId(Long userId);
}
