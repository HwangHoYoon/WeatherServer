package com.jagiya.alarm.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AlarmLocationWeatherResponse {

    @Schema(description = "시도", example = "부천시", name = "cityDo")
    private String cityDo;

    @Schema(description = "구군", example = "원미구", name = "guGun")
    private String guGun;

    @Schema(description = "읍면동", example = "상동", name = "eupMyun")
    private String eupMyun;

    @Schema(description = "법정동코드", example = "1111010300", name = "regionCd")
    private String regionCd;

    @Schema(description = "AM, PM, ALL", example = "AM", name = "timeOfDay")
    private String timeOfDay;

    @Schema(description = "비가오는지역여부", example = "true", name = "locationRain")
    private boolean locationRain;

    @Schema(description = "알람지역날씨목록", name = "alarmLocationWeatherList")
    private List<AlarmLocationWeatherDataResponse> alarmLocationWeatherList;
}
