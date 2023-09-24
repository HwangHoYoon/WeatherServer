package com.jagiya.main.repository;

import com.jagiya.main.entity.Temp;
import com.jagiya.main.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

//        Optional<Users> findByUsersId(long usersId);

}
