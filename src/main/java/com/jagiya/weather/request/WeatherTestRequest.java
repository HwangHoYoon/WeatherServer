package com.jagiya.weather.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class WeatherTestRequest {
    @Schema(description = "법정동코드")
    private String code;
    @Schema(description = "날짜")
    private String fcstDate;

    @Schema(description = "X좌표")
    private String latX;

    @Schema(description = "Y좌표")
    private String lonY;

    @Schema(description = "갱신타입")
    private String refreshType;
}
