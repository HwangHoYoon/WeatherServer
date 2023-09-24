package com.jagiya.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jagiya.common.utils.DateUtils;
import com.jagiya.weather.enums.WeatherCategory;
import com.jagiya.weather.enums.WeatherCode;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherTestResponse {

    @JsonProperty("발표일자")
    private String baseDate;

    @JsonProperty("발표시각")
    private String baseTime;

    @JsonProperty("예보일자")
    private String fcstDate;

    @JsonProperty("예보시각")
    private String fcstTime;

    @JsonProperty("강수확률")
    private String pop;

    @JsonProperty("강수형태")
    private String pty;

    @JsonProperty("1시간강수량")
    private String pcp;

    @JsonProperty("하늘상태")
    private String sky;

    @JsonProperty("1시간기온")
    private String tmp;

    @JsonProperty("일최저기온")
    private String tmn;

    @JsonProperty("일최기온")
    private String tmx;

    public String getBaseDate() {
        return DateUtils.getStringDateFormat("yyyyMMdd", "yyyy-MM-dd", baseDate);
    }

    public String getBaseTime() {
        return DateUtils.getStringTimeFormat("HHmm", "HH:mm", baseTime);
    }

    public String getFcstDate() {
        return DateUtils.getStringDateFormat("yyyyMMdd", "yyyy-MM-dd", fcstDate);
    }

    public String getFcstTime() {
        return DateUtils.getStringTimeFormat("HHmm", "HH:mm", fcstTime);
    }

    public String getPop() {
        return pop + WeatherCategory.POP.getUnits();
    }

    public String getPty() {
        return WeatherCode.getValueByCategoryAndCode(WeatherCategory.PTY.name(), pty);
    }

    public String getPcp() {
        return "강수없음".equals(pcp) ? pcp : pcp + WeatherCategory.PCP.getUnits();
    }

    public String getSky() {
        return WeatherCode.getValueByCategoryAndCode(WeatherCategory.SKY.name(), sky);
    }

    public String getTmp() {
        return tmp + WeatherCategory.TMP.getUnits();
    }

    public String getTmn() {
        return "".equals(tmn) ? tmn : tmn + WeatherCategory.TMN.getUnits();
    }

    public String getTmx() {
        return "".equals(tmx) ? tmx : tmx + WeatherCategory.TMX.getUnits();
    }

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
}
