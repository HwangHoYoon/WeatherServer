package com.jagiya.main.repository;

import com.jagiya.main.entity.Token;
import com.jagiya.main.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {


}
