package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmDeleteRequest {
    @Schema(description = "알람ID", example = "1", name = "alarmId")
    private Long alarmId;
}
