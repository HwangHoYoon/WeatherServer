package com.jagiya.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmLocationNotiResponse {
    @Schema(description = "시도", example = "부천시", name = "cityDo")
    private String cityDo;
    @Schema(description = "구군", example = "원미구", name = "guGun")
    private String guGun;
    @Schema(description = "읍면동", example = "상동", name = "eupMyun")
    private String eupMyun;
    @Schema(description = "지역외 개수", example = "1", name = "locationCnt")
    private Integer locationCnt;
    @Schema(description = "AM, PM, ALL", example = "AM", name = "timeOfDay")
    private String timeOfDay;
    @Schema(description = "다시울림분", example = "35", name = "reminder")
    private String reminder;

}
