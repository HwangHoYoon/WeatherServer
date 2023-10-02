package com.jagiya.location.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LocationRequest {

    @Schema(description = "법정동코드")
    private String regionCd;

    @Schema(description = "시도")
    private String cityDo;

    @Schema(description = "구군")
    private String guGun;

    @Schema(description = "읍면동")
    private String eupMyun;

    @Schema(description = "위도")
    private String lat;

    @Schema(description = "경도")
    private String lon;

    @Schema(description = "X좌표값")
    private String LatX;
    
    @Schema(description = "Y좌표값")
    private String LonY;
}
