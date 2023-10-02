package com.jagiya.alarm.response;

import com.jagiya.alarm.request.AlarmLocationTimeRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlarmLocationDetailResponse {
    @Schema(description = "알람지역ID", example = "1", name = "alarmLocationId")
    private Long alarmLocationId;

    @Schema(description = "법정동코드", example = "1111010300", name = "regionCd")
    private String regionCd;

    @Schema(description = "시도", example = "수원시", name = "cityDo")
    private String cityDo;

    @Schema(description = "구군", example = "팔달구", name = "guGun")
    private String guGun;

    @Schema(description = "읍면동", example = "중동", name = "eupMyun")
    private String eupMyun;

    @Schema(description = "알람지역시간목록",  name = "alarmLocationTimeDetail")
    private List<AlarmLocationTimeDetailResponse> alarmLocationTimeDetail;
}
