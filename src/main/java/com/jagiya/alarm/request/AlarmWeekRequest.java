package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmWeekRequest {

    @Schema(description = "알람요일ID", example = "1", name = "alarmWeekId")
    private Long alarmWeekId;

    @Schema(description = "요일코드(1부터 월요일)", example = "1", name = "weekId")
    private long weekId;
}
