package com.jagiya.main.dto;

import com.jagiya.main.entity.Test;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestReq {
    private Long id;
    private String name;

    public Test toEntity() {
        return Test.builder()
                .id(id)
                .name(name)
                .build();
    }
}
