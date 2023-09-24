package com.jagiya.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoginType {
    NONE(0, "none"),
    KAKAO(1, "kakao"),
    APPLE(2, "apple"),
    ;

    private final Integer code;
    private final String name;

    private static final LoginType[] VALUES;

    static {
        VALUES = values();
    }

    public static String getLoginTypeName(Integer code) {
        for (LoginType status : VALUES) {
            if (status.code == code) {
                return status.getName();
            }
        }
        return "";
    }
}
