package com.jagiya.alarm.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlarmWeekInsertRequest {

    @Schema(description = "요일코드(1부터 월요일)", example = "1", name = "weekId")
    @NotBlank(message = "요일은 필수입니다.")
    @Min(value = 1, message = "요일은 월요일보다 작을 수 없습니다.")
    @Max(value = 7, message = "요일은 일요일보다 클 수 없습니다.")
    private long weekId;
}
