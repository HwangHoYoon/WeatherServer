package com.jagiya.login.repository;

import com.jagiya.login.entity.User;
import com.jagiya.main.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, Long> {
    Optional<User> findBySnsTypeAndSnsId(Integer SnsType, String snsId);
}
