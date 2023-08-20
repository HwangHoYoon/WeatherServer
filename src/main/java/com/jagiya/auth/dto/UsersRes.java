package com.jagiya.auth.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class UsersRes {
    private final Long usersId;

    private final Long id;

    private final String username;

    private final String nickname;

    private final String email;

    private final Integer snsType;

    private final String snsName;

    private final String snsProfile;

    private final String gender;

    private final String uuid;

    private final String ci;

    private final Integer deleteFlag;

    private final Integer agreesFalg;

    private final Integer isAdmin;

    private final Date birthday;

    private final Date ciDate;
    private final Date snsConnectDate;

    private final Date regDate;

    private final Date modifyDate;

    private final Date agreesDate;

    @Builder
    public UsersRes(Long usersId, Long id, String username, String nickname, String email, Integer snsType, String snsName, String snsProfile, String gender, String uuid, String ci, Integer deleteFlag, Integer agreesFalg, Integer isAdmin, Date birthday, Date ciDate, Date snsConnectDate, Date regDate, Date modifyDate, Date agreesDate) {
        this.usersId = usersId;
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.snsType = snsType;
        this.snsName = snsName;
        this.snsProfile = snsProfile;
        this.gender = gender;
        this.uuid = uuid;
        this.ci = ci;
        this.deleteFlag = deleteFlag;
        this.agreesFalg = agreesFalg;
        this.isAdmin = isAdmin;
        this.birthday = birthday;
        this.ciDate = ciDate;
        this.snsConnectDate = snsConnectDate;
        this.regDate = regDate;
        this.modifyDate = modifyDate;
        this.agreesDate = agreesDate;
    }
}
