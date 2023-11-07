package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmLocationTimeInsertRequest {

    @Schema(description = "지역시간", example = "1100", name = "locationTime")
    private String locationTime;
}
