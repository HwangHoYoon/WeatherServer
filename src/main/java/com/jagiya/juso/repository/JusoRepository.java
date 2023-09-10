package com.jagiya.juso.repository;

import com.jagiya.juso.entity.Juso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JusoRepository extends JpaRepository<Juso, Long> {

    Optional<Juso> findByCode(String code);

}
