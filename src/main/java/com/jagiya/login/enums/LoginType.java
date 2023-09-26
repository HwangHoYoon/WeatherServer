package com.jagiya.login.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoginType {
    NONE(0, "비회원"),
    KAKAO(1, "카카오"),
    APPLE(2, "애플"),
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
