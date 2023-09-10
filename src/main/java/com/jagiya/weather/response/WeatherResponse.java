package com.jagiya.weather.response;

import lombok.Data;

@Data
public class WeatherResponse {
    private WeatherBody body;
    private WeatherHeader header;
}
