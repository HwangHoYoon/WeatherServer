package com.jagiya.weather.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WeatherCode {
    SKY_SUNNY("SKY","1", "맑음"),
    SKY_S_CLOUD("SKY","2", "구름적음"),
    SKY_L_CLOUD("SKY","3", "구름많음"),
    SKY_BLUR("SKY","4", "흐림"),

    PTY_NONE("PTY","0", "없음"),
    PTY_RAIN("PTY","1", "비"),
    PTY_RAIN_SNOW("PTY","2", "비/눈"),
    PTY_SNOW("PTY","3", "눈"),
    PTY_SHOWER("PTY","4", "소나기"),
    PTY_RAINDROP("PTY","5", "빗방울"),
    PTY_RAINDROP_SNOWFALL("PTY","6", "빗방울눈날림"),
    PTY_SNOWFALL("PTY","7", "눈날림");

    private final String category;

    private final String code;

    private final String value;

    public static String getValueByCategoryAndCode(String category, String code) {
        for (WeatherCode weatherCode : WeatherCode.values()) {
            if (weatherCode.getCategory().equals(category) && weatherCode.getCode().equals(code)) {
                return weatherCode.getValue();
            }
        }
        return "";
    }
}
