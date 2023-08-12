package com.jagiya.main.repository;

import com.jagiya.main.entity.SnsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsInfoRepository extends JpaRepository<SnsInfo, Long> {

    Optional<SnsInfo> findBySnsTypeAndSnsProfile(Integer SnsType, String SnsProfile);
}
