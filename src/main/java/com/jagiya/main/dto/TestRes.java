package com.jagiya.main.dto;

import com.jagiya.main.entity.Test;
import lombok.Getter;

@Getter
public class TestRes {
    private final Long id;
    private final String name;

    public TestRes(Test test) {
        this.id = test.getId();
        this.name = test.getName();
    }
}
