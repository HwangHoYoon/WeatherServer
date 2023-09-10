package com.jagiya.weather.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class WeatherTestRequest {
    @Column(name = "code")
    @Schema(description = "법정동코드")
    private String code;
    @Column(name = "fcstDate")
    @Schema(description = "날짜")
    private String fcstDate;

    @Column(name = "latX")
    @Schema(description = "X좌표")
    private String latX;

    @Column(name = "lonY")
    @Schema(description = "Y좌표")
    private String lonY;
}
