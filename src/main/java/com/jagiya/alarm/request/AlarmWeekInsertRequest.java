package com.jagiya.alarm.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmWeekInsertRequest {

    @Schema(description = "요일코드(1부터 월요일)", example = "1", name = "weekId")
    private long weekId;
}
