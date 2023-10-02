package com.jagiya.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmLocationWeatherDataResponse {

    @Schema(description = "비오는여부", example = "true", name = "rain")
    private boolean rain;
    @Schema(description = "AM, PM", example = "AM", name = "timeOfDay")
    private String timeOfDay;
    @Schema(description = "지역설정시간", example = "0800", name = "locationTime")
    private String locationTime;

}
