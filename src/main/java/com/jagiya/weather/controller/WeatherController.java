package com.jagiya.weather.controller;

import com.jagiya.weather.entity.Weather;
import com.jagiya.weather.request.WeatherTestRequest;
import com.jagiya.weather.response.WeatherTestResponse;
import com.jagiya.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Weather", description = "날씨 API Document")
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "행정코드별 날씨 조회", description = "행정코드별 날씨 조회")
    @GetMapping("/getLocationForWeather")
    @Parameter(name = "행정코드와 기준날짜", description = "행정코드와 기준날짜")
    public List<WeatherTestResponse> getLocationForWeather(WeatherTestRequest weatherRequest) {
        return weatherService.selectLocationForWeather(weatherRequest);
    }

    @Operation(summary = "행정코드별 날씨 갱신", description = "행정코드별 날씨 갱신")
    @GetMapping("/refreshLocationForWeather")
    @Parameter(name = "행정코드와 갱신타입", description = "행정코드와 갱신타입")
    public List<WeatherTestResponse> refreshLocationForWeather(WeatherTestRequest weatherRequest) {
        return weatherService.refreshLocationForWeather(weatherRequest);
    }

}
