package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "AlarmInsertRequest")
public class AlarmInsertRequest {

    @Schema(description = "유저ID", example = "1", name = "userId")
    @NotBlank(message = "유저Id는 필수입니다.")
    private Long userId;

    @Schema(description = "시간(오후 1시30분이면 0130)", example = "0130", name = "alarmTime")
    @NotBlank(message = "알람시간은 필수입니다.")
    private String alarmTime;

    @Schema(description = "AM 또는 PM", example = "AM", name = "timeOfDay")
    @NotBlank(message = "AM 또는 PM은 필수입니다.")
    private String timeOfDay;

    @Schema(description = "다시울림시간(1분부터59분까지)", example = "10", name = "reminder")
    @NotBlank(message = "다시울림시간은 필수입니다.")
    private String reminder;

    @Schema(description = "진동여부(0:없음, 1:진동)", example = "0", name = "vibration")
    @NotBlank(message = "진동여부는 필수입니다.")
    private Integer vibration;

    @Schema(description = "요일목록",  name = "weekList")
    private List<AlarmWeekInsertRequest> weekList;

    @Schema(description = "알람소리코드", example = "1", name = "alarmSoundId")
    @NotBlank(message = "알람소리는 필수입니다.")
    private Long alarmSoundId;

    @Schema(description = "볼륨(1부터 10까지)", example = "1", name = "volume")
    @NotBlank(message = "볼륨은 필수입니다.")
    @Min(value = 1, message = "볼륨은 1보다 작을 수 없습니다.")
    @Max(value = 10, message = "볼륨은 10보다 클 수 없습니다.")
    private Integer volume;

    @Schema(description = "알람지역목록",  name = "alarmLocationList")
    private List<AlarmLocationInsertRequest> alarmLocationList;
}
