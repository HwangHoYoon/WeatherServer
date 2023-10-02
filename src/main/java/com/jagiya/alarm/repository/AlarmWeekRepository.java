package com.jagiya.alarm.repository;

import com.jagiya.alarm.entity.Alarm;
import com.jagiya.alarm.entity.AlarmWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmWeekRepository extends JpaRepository<AlarmWeek, Long> {

    List<AlarmWeek> findByAlarmAlarmId(Long alarmId);

    Long deleteByAlarmAlarmId(Long alarmId);

}
