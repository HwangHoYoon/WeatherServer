package com.jagiya.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmLocationResponse {

    @JsonProperty("cityDo")
    private String cityDo;

    @JsonProperty("guGun")
    private String guGun;

    @JsonProperty("eupMyun")
    private String eupMyun;

    @JsonProperty("timeOfDay")
    private String timeOfDay;
}
