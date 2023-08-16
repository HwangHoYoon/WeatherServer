package com.jagiya.main.dto;

import com.jagiya.main.entity.Test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Schema(description = "Test 응답 VO")
public class TestRes {

    @Schema(description = "테스트 아이디")
    private final Long id;

    @Schema(description = "테스트 이름")
    private final String name;

    public TestRes(Test test) {
        this.id = test.getId();
        this.name = test.getName();
    }
}
