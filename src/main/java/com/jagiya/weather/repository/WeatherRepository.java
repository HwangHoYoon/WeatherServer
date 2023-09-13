package com.jagiya.weather.repository;

import com.jagiya.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByJusoGroupJusoGroupIdAndFcstDateOrderByFcstTimeAsc(Long jusoGroupId, String fcstDate);

    Weather findByJusoGroupJusoGroupIdAndFcstDateAndFcstTime(Long jusoGroupId, String fcstDate, String fcstTime);

}
