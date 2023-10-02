package com.jagiya.alarm.repository;

import com.jagiya.alarm.entity.AlarmLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmLocationRespository extends JpaRepository<AlarmLocation, Long> {

    List<AlarmLocation> findByAlarmAlarmIdOrderByAlarmLocationId(Long alarmId);

    List<AlarmLocation> findByAlarmAlarmId(Long alarmId);

    Long deleteByAlarmAlarmId(Long alarmId);


}
