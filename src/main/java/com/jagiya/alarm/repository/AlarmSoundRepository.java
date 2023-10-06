package com.jagiya.alarm.repository;

import com.jagiya.alarm.entity.AlarmSound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmSoundRepository extends JpaRepository<AlarmSound, Long> {
}
