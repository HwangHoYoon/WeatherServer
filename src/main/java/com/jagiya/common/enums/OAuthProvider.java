package com.jagiya.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OAuthProvider {
    KAKAO(0, "kakao"),
    APPLE(1, "apple");

    private final Integer code;
    private final String name;
}
