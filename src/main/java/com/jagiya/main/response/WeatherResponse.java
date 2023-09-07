package com.jagiya.main.response;

import lombok.Data;

@Data
public class WeatherResponse {
    private WeatherBody body;
    private WeatherHeader header;
}
