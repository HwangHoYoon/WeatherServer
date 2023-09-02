package com.jagiya.main.dto;

import com.jagiya.main.entity.Test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(description = "Test 요청 VO")
public class TestReq {

    @Schema(description = "테스트 아이디")
    private Long id;

    @Schema(description = "테스트 이름")
    private String name;

    public Test toEntity() {
        return Test.builder()
                .id(id)
                .name(name)
                .build();
    }
}
