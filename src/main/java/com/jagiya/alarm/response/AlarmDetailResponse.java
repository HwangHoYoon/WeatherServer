package com.jagiya.alarm.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AlarmDetailResponse {

    @Schema(description = "유저ID", example = "1", name = "userId")
    private Long userId;

    @Schema(description = "알람ID", example = "1", name = "alarmId")
    private Long alarmId;

    @Schema(description = "활성화여부(0 : 비활성화, 1 : 활성화)", example = "1", name = "enabled")
    private Integer enabled;

    @Schema(description = "진동여부(0: 비활성화, 1 : 활성화)", example = "1", name = "vibration")
    private Integer vibration;

    @Schema(description = "볼륨", example = "1", name = "volume")
    private Integer volume;

    @Schema(description = "다시울림", example = "15", name = "reminder")
    private String reminder;

    @Schema(description = "알람시간(AM PM 시간)", example = "0800", name = "alarmTime")
    private String alarmTime;

    @Schema(description = "AM, PM", example = "AM", name = "timeOfDay")
    private String timeOfDay;

    @Schema(description = "알람지역목록", name = "alarmLocation")
    private List<AlarmLocationDetailResponse> alarmLocation;

    @Schema(description = "알람요일목록", name = "alarmWeek")
    private List<AlarmWeekResponse> alarmWeek;

    @Schema(description = "알람소리코드", example = "1", name = "alarmSoundId")
    private Long alarmSoundId;
}
