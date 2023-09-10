package com.jagiya.weather.repository;

import com.jagiya.auth.entity.Token;
import com.jagiya.main.entity.Users;
import com.jagiya.weather.entity.Juso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JusoRepository extends JpaRepository<Juso, Long> {

    Optional<Juso> findByCode(String code);

}
