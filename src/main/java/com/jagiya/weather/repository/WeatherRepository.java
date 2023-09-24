package com.jagiya.weather.repository;

import com.jagiya.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByLocationGroupLocationGroupIdAndFcstDateOrderByFcstTimeAsc(Long locationGroupId, String fcstDate);

    Weather findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime(Long locationoGroupId, String fcstDate, String fcstTime);

}
