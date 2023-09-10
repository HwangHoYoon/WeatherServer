package com.jagiya.juso.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class JusoTestResponse {

    @JsonProperty("시도명")
    private String siNm;

    @JsonProperty("시군구명")
    private String sggNm;

    @JsonProperty("읍면동명")
    private String emdNm;

    @JsonProperty("행정구역코드")
    private String admCd;

}
