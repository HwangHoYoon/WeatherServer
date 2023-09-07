package com.jagiya.main.schedule;

import com.jagiya.main.service.Impl.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherScheduledTask {

    private final WeatherService weatherService;

    @Scheduled(cron = "0 27 2 * * ?")
    public void runTask() throws Exception {
        // 주기적으로 실행할 작업 수행
        System.out.println("Scheduled Task executed at every minute.");
        weatherService.insertWeatherWithCode();
    }
}
