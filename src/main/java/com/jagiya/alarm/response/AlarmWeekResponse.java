package com.jagiya.alarm.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmWeekResponse {
    private Long alarmWeekId;
    private Long weekId;
}
