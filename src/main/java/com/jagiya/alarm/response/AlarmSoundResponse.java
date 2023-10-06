package com.jagiya.alarm.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmSoundResponse {

    @Schema(description = "알람소리ID", example = "1", name = "alarmSoundId")
    private Long alarmSoundId;

    @Schema(description = "알람소리이름", example = "기본소리", name = "alarmSoundName")
    private String alarmSoundName;

    @Schema(description = "파일이름", example = "nomal.mp3", name = "fileName")
    private String fileName;
}
