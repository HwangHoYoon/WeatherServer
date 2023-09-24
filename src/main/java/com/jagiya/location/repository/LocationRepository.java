package com.jagiya.location.repository;

import com.jagiya.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByRegionCd(String regionCd);

    List<Location> findByCityDoIn(List<String> cityDos);

}
