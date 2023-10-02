package com.jagiya.alarm.repository;

import com.jagiya.alarm.entity.AlarmLocationTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmLocationTimeRespository extends JpaRepository<AlarmLocationTime, Long> {

    List<AlarmLocationTime> findByAlarmLocationAlarmLocationIdOrderByAlarmLocationTimeId(Long alarmLocationId);


    List<AlarmLocationTime> findByAlarmLocationAlarmLocationId(Long alarmLocationId);


    Long deleteByAlarmLocationAlarmLocationId(Long alarmLocationId);

}
