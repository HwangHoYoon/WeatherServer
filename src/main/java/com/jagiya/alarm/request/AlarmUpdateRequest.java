package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "AlarmUpdateRequest")
public class AlarmUpdateRequest {

    @Schema(description = "유저ID", example = "1", name = "userId")
    private Long userId;

    @Schema(description = "알람ID", example = "1", name = "alarmId")
    private Long alarmId;

    @Schema(description = "시간(오후 1시30분이면 0130)", example = "0130", name = "alarmTime")
    private String alarmTime;

    @Schema(description = "AM 또는 PM", example = "AM", name = "timeOfDay")
    private String timeOfDay;

    @Schema(description = "다시울림시간(1분부터59분까지)", example = "10", name = "reminder")
    private String reminder;

    @Schema(description = "진동여부(0:없음, 1:진동)", example = "0", name = "vibration")
    private Integer vibration;

    @Schema(description = "요일목록",  name = "weekList")
    private List<AlarmWeekRequest> weekList;

    @Schema(description = "알람소리코드", example = "1", name = "alarmSoundId")
    private Long alarmSoundId;

    @Schema(description = "볼륨(1부터 10까지)", example = "1", name = "volume")
    private Integer volume;

    @Schema(description = "알람지역목록",  name = "alarmLocationList")
    private List<AlarmLocationRequest> alarmLocationList;
}
