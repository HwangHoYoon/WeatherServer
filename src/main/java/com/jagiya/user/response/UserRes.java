package com.jagiya.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRes {

    private final Long userId;

    private final String snsId;

    private final String name;

    private final String email;
    private final String snsName;

    @Builder
    public UserRes(Long userId, String snsId, String name, String email, String snsName) {
        this.userId = userId;
        this.snsId = snsId;
        this.name = name;
        this.email = email;
        this.snsName = snsName;
    }
}
