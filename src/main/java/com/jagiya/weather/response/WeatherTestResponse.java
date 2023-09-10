package com.jagiya.weather.response;

import com.jagiya.common.enums.WeatherCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Setter;

public class WeatherTestResponse {

    @Schema(description = "발표일자")
    private String baseDate;

    @Schema(description = "발표시각")
    private String baseTime;

    @Schema(description = "예보일자")
    private String fcstDate;

    @Schema(description = "예보시각")
    private String fcstTime;

    @Schema(description = "강수확률")
    private String pop;

    @Schema(description = "강수형태")
    private String pty;

    @Schema(description = "1시간강수량")
    private String pcp;

    @Schema(description = "하늘상태")
    private String sky;

    @Schema(description = "1시간기온")
    private String tmp;

    @Schema(description = "일최저기온")
    private String tmn;

    @Schema(description = "일최기온")
    private String tmx;

    @Builder
    public WeatherTestResponse(String baseDate, String baseTime, String fcstDate, String fcstTime, String pop, String pty, String pcp, String sky, String tmp, String tmn, String tmx) {
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.pop = pop;
        this.pty = pty;
        this.pcp = pcp;
        this.sky = sky;
        this.tmp = tmp;
        this.tmn = tmn;
        this.tmx = tmx;
    }

    @Override
    public String toString() {
        return "{" +
                "발표일자='" + baseDate + '\'' +
                ", 발표시각='" + baseTime + '\'' +
                ", 예보일자='" + fcstDate + '\'' +
                ", 예보시각='" + fcstTime + '\'' +
                ", " + WeatherCategory.POP.getName() + "'="  + pop + WeatherCategory.POP.getUnits() + '\'' +
                ", " + WeatherCategory.PTY.getName() + "'="  + pty + WeatherCategory.PTY.getUnits() + '\'' +
                ", " + WeatherCategory.PCP.getName() + "'="  + pcp + WeatherCategory.PCP.getUnits() + '\'' +
                ", " + WeatherCategory.SKY.getName() + "'="  + sky + WeatherCategory.SKY.getUnits() + '\'' +
                ", " + WeatherCategory.TMP.getName() + "'="  + tmp + WeatherCategory.TMP.getUnits() + '\'' +
                ", " + WeatherCategory.TMX.getName() + "'="  + tmx + WeatherCategory.TMX.getUnits() + '\'' +
                ", " + WeatherCategory.TMN.getName() + "'="  + tmn + WeatherCategory.TMN.getUnits() + '\'' +
                '}';
    }
}
