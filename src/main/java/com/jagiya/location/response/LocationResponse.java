package com.jagiya.location.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

@Data
public class LocationResponse {

    @Schema(description = "시도명)", example = "경기도", name = "cityDo")
    private String cityDo;

    @Schema(description = "시군구명", example = "부천시", name = "guGun")
    private String guGun;

    @Schema(description = "읍면동명", example = "상동", name = "eupMyun")
    private String eupMyun;

    @Schema(description = "행정구역코드", example = "4119010900", name = "regionCd")
    private String regionCd;

}
