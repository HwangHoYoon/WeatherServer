package com.jagiya.location.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class LocationTestResponse {

    @JsonProperty("시도명")
    private String siNm;

    @JsonProperty("시군구명")
    private String sggNm;

    @JsonProperty("읍면동명")
    private String emdNm;

    @JsonProperty("행정구역코드")
    private String admCd;

    @JsonProperty("위도")
    private String lat;

    @JsonProperty("경도")
    private String lon;
    
    @JsonProperty("X좌표")
    private String latX;

    @JsonProperty("Y좌표")
    private String lonY;

}
