package com.jagiya.auth.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class UsersEditor {

    private String email;
    private String nickname;
    private String username;
    private String snsProfile;
    private Date birthDay;
    private String gender;
    private Date snsConnectDate;
    private Date modifyDate;
    private Long id;

    @Builder
    public UsersEditor(String email, String nickname, String username, String snsProfile, Date birthDay, String gender, Date snsConnectDate, Date modifyDate, Long id) {
        this.email = email;
        this.nickname = nickname;
        this.username = username;
        this.snsProfile = snsProfile;
        this.birthDay = birthDay;
        this.gender = gender;
        this.snsConnectDate = snsConnectDate;
        this.modifyDate = modifyDate;
        this.id = id;
    }
}
