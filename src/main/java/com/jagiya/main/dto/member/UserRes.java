package com.jagiya.main.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

@Getter
public class UserRes {

    private final Long userId;
    private final String snsId;
    private final String name;
    private final String email;
    private final String snsName;
    private final Integer deleteFlag;
    private final Date deleteDate;

    @Builder
    public UserRes(Long userId, String snsId, String name, String email, String snsName, Integer deleteFlag, Date deleteDate) {
        this.userId = userId;
        this.snsId = snsId;
        this.name = name;
        this.email = email;
        this.snsName = snsName;
        this.deleteFlag = deleteFlag;
        this.deleteDate = deleteDate;
    }
}