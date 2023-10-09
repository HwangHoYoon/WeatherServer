package com.jagiya.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "userDetailUpdateRequest")
public class UserDetailUpdateRequest {

    @Schema(description = "유저ID", example = "1", name = "userId")
    private Long userId;

    @Schema(description = "유저이름", example = "이름", name = "name")
    private String name;
}
