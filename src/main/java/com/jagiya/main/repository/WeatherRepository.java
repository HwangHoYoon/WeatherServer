package com.jagiya.main.repository;

import com.jagiya.main.entity.Temp;
import com.jagiya.main.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
