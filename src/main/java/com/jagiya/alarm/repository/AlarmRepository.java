package com.jagiya.alarm.repository;

import com.jagiya.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByUserUserId(Long userId);

    List<Alarm> findByEnabledAndAlarmTimeBetween(Integer enabled, String fromTime, String toTime);
}
