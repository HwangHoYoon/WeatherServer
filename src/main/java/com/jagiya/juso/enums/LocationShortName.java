package com.jagiya.juso.enums;

import com.jagiya.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum LocationShortName {

    SEOUL("서울특별시", "서울"),
    BUSAN("부산광역시", "부산"),
    DAEGU("대구광역시", "대구"),
    INCHEON("인천광역시", "인천"),
    GWANGJU("광주광역시", "광주"),
    ULSAN("울산광역시", "울산"),
    SEJONG("세종특별자치시", "세종"),
    GYEONGGI("경기도", "경기"),
    GANGWON("강원특별자치도", "강원"),
    CHUNGCHEONGBUK("충청북도", "충북"),
    CHUNGCHEONGNAM("충청남도", "충남"),
    JEOLLABUK("전라북도", "전북"),
    JEOLLANAM("전라남도", "전남"),
    GYEONGSANGBUK("경상북도", "경북"),
    GYEONGSANGNAM("경상남도", "경남"),
    JEJU("제주특별자치도", "제주");

    private String orginName;
    private String shortName;

    private static final LocationShortName[] VALUES;

    static {
        VALUES = values();
    }

    public static String getShortName(String name) {
        for (LocationShortName status : VALUES) {
            if (status.orginName.equals(name)) {
                return status.getShortName();
            }
        }
        return name;
    }
}
