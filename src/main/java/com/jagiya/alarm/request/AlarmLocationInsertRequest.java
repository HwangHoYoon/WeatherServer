package com.jagiya.alarm.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AlarmLocationInsertRequest {

    @Schema(description = "법정동코드", example = "1111010300", name = "regionCd")
    private String regionCd;

    @Schema(description = "시도", example = "수원시", name = "cityDo")
    private String cityDo;

    @Schema(description = "구군", example = "팔달구", name = "guGun")
    private String guGun;

    @Schema(description = "읍면동", example = "중동", name = "eupMyun")
    private String eupMyun;

    @Schema(description = "알람지역시간목록",  name = "alarmLocationTimeRequest")
    private List<AlarmLocationTimeInsertRequest> alarmLocationTimeRequest;

}
