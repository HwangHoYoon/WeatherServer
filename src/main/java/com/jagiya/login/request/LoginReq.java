package com.jagiya.login.request;

import com.jagiya.login.dto.UsersRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class LoginReq {

    @Schema(description = "소셜 계정 ID (비회원은 디바이스 ID)", example = "example1", name = "snsId")
    private String snsId;

    @Schema(description = "이름", example = "홍길동", name = "name")
    private String name;

    @Schema(description = "이메일 (선택 동의했을 경우)", example = "example@naver.com", name = "email")
    private String email;

    @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType")
    private Integer snsType;

}
