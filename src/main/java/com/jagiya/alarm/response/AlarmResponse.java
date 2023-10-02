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

    @Schema(description = "알람ID", example = "1", name = "alarmId")
    private Long alarmId;

    @Schema(description = "활성화여부(0 : 비활성화, 1 : 활성화)", example = "1", name = "enabled")
    private Integer enabled;

    @Schema(description = "진동여부(0: 비활성화, 1 : 활성화)", example = "1", name = "vibration")
    private Integer vibration;

    @Schema(description = "알람시간(AM PM 시간)", example = "0800", name = "alarmTime")
    private String alarmTime;

    @Schema(description = "AM, PM", example = "AM", name = "timeOfDay")
    private String timeOfDay;

    @Schema(description = "알람지역목록", name = "alarmLocation")
    private List<AlarmLocationResponse> alarmLocation;

    @Schema(description = "알람요일목록", name = "alarmWeek")
    private List<AlarmWeekResponse> alarmWeek;

}
