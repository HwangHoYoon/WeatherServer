package com.jagiya.location.repository;

import com.jagiya.location.entity.Location;
import com.jagiya.location.entity.RecentLocation;
import com.jagiya.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecentLocationRepository extends JpaRepository<RecentLocation, Long> {

    Optional<RecentLocation> findByUserTbAndRegionCd(User usersInfo, String regionCd);

    List<RecentLocation> findTop10ByUserTbOrderByRegDateDesc(User usersInfo);

}
