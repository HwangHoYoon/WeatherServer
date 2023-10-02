package com.jagiya.alarm.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmLocationTimeDetailResponse {

    @Schema(description = "지역시간ID", example = "1", name = "alarmLocationTimeId")
    private Long alarmLocationTimeId;

    @Schema(description = "지역시간", example = "1100", name = "locationTime")
    private String locationTime;
}
