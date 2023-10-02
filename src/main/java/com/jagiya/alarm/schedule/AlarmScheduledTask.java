package com.jagiya.alarm.schedule;

import com.jagiya.alarm.service.AlarmService;
import com.jagiya.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmScheduledTask {
    private final AlarmService alarmService;
    @Scheduled(cron = "0 45 * * * ?")
    public void runTask() throws Exception {
        log.info("alarm Scheduled start");
        alarmService.refreshAlarmLocationWeather();
        log.info("alarm Scheduled end");
    }
}
