package com.jagiya.juso.repository;

import com.jagiya.juso.entity.Juso;
import com.jagiya.juso.entity.JusoGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JusoGroupRepository extends JpaRepository<JusoGroup, Long> {

}