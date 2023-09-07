package com.jagiya.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum WeatherCategory {

    POP( "강수확률", "%"),
    PTY( "강수형태", ""),
    PCP( "1시간 강수량", "mm"),
    SKY( "하늘상태", ""),
    TMP( "1시간 기온", "℃"),
    TMN( "일 최저기온", "℃"),
    TMX( "일 최고기온", "℃"),
    T1H( "기온", "℃"),
    RN1( "1시간 강수량", "mm");

    private final String name;

    private final String units;
}
