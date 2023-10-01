package com.jagiya.alarm.repository;

import com.jagiya.alarm.entity.Alarm;
import com.jagiya.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByUserUserId(Long userId);


}
