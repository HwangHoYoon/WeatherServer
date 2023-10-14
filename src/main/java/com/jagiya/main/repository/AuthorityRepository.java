package com.jagiya.main.repository;

import com.jagiya.main.entity.Authority;
import com.jagiya.main.entity.Users;
import com.jagiya.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByDeviceId(String deviceId);

}
