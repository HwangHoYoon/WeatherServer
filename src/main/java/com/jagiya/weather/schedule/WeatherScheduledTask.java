package com.jagiya.weather.schedule;

import com.jagiya.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherScheduledTask {

    private final WeatherService weatherService;

    @Scheduled(cron = "0 00 6 * * ?")
    public void runTask() throws Exception {
        log.info("weather Scheduled start");
        weatherService.insertWeather();
        log.info("weather Scheduled end");
    }
}
