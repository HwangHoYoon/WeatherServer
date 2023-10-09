package com.jagiya.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailResponse {

    @Schema(description = "유저ID", example = "1", name = "userId")
    private Long userId;

    @Schema(description = "이름", example = "이름", name = "name")
    private String name;

    @Schema(description = "이메일", example = "example@naver.com", name = "email")
    private String email;
}
