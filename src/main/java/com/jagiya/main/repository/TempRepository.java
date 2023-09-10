package com.jagiya.main.repository;

import com.jagiya.main.entity.Temp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TempRepository extends JpaRepository<Temp, Long> {
        List<Temp> findByFld06AndFld08(String Fld06, String Fld08);

        List<Temp> findByFld06(String Fld06);
}
