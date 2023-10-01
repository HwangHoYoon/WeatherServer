package com.jagiya.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlarmResponse {

    @JsonProperty("alarmId")
    private Long alarmId;

    @JsonProperty("enabled")
    private Integer enabled;

    @JsonProperty("vibration")
    private Integer vibration;

    @JsonProperty("alarmTime")
    private String alarmTime;

    @JsonProperty("timeOfDay")
    private String timeOfDay;

    @JsonProperty("alarmLocation")
    private List<AlarmLocationResponse> alarmLocation;

    @JsonProperty("alarmWeek")
    private List<AlarmWeekResponse> alarmWeek;

}
