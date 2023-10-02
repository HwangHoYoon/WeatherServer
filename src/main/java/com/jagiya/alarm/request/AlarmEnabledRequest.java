package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class AlarmEnabledRequest {
    @Schema(description = "알람ID", example = "1", name = "alarmId")
    private Long alarmId;
    @Schema(description = "활성화여부(0:비활성화, 1:활성화)", example = "0", name = "enabled")
    private Integer enabled;
}
