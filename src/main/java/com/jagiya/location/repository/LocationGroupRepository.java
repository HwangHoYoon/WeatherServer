package com.jagiya.location.repository;

import com.jagiya.location.entity.LocationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationGroupRepository extends JpaRepository<LocationGroup, Long> {
    Optional<LocationGroup> findByLatXAndLonY(String latX, String lonY);
}