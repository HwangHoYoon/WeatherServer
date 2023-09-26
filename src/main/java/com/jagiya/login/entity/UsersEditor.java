package com.jagiya.login.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class UsersEditor {

    private String email;
    private String name;
    private String snsId;
    private Date modifyDate;

    @Builder
    public UsersEditor(String email, String name, Date modifyDate, String snsId) {
        this.email = email;
        this.name = name;
        this.modifyDate = modifyDate;
        this.snsId = snsId;
    }
}
