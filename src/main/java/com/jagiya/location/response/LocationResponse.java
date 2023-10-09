package com.jagiya.location.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Data
public class LocationResponse {

    @JsonProperty("시도명")
    private String cityDo;

    @JsonProperty("시군구명")
    private String guGun;

    @JsonProperty("읍면동명")
    private String eupMyun;

    @JsonProperty("행정구역코드")
    private String regionCd;

}
