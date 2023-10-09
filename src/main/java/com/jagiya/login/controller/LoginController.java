package com.jagiya.login.controller;

import com.jagiya.login.dto.UsersRes;
import com.jagiya.login.response.UserRes;
import com.jagiya.login.service.LoginService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "login", description = "로그인 API")
@RequestMapping("auth")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "로그인", description = "로그인")
    @GetMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public UserRes login(@Schema(description = "소셜 계정 ID (비회원은 디바이스 ID)", example = "example1", name = "snsId") @NotBlank(message = "소셜계정ID를 입력해주세요.") String snsId,
                         @Schema(description = "이름", example = "홍길동", name = "name") @Nullable String name,
                         @Schema(description = "이메일 (선택 동의했을 경우)", example = "example@naver.com", name = "email") @Nullable String email,
                         @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType
    ) {
        return loginService.login(snsId, name, email, snsType);
    }

    @Operation(summary = "회원전환 로그인", description = "회원전환 로그인")
    @GetMapping("/loginAndUserTransform ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public UserRes loginAndUserTransform(@Schema(description = "비회원 계정 ID", example = "1", name = "userId") @NotBlank(message = "비회원 UserID를 입력해주세요.") Long userId,
                         @Schema(description = "소셜계정 ID", example = "example2", name = "tobeSnsId") @NotBlank(message = "소셜계정ID를 입력해주세요.") String tobeSnsId,
                         @Schema(description = "이름", example = "홍길동", name = "name") @Nullable String name,
                         @Schema(description = "이메일 (선택 동의했을 경우)", example = "example@naver.com", name = "email") @Nullable String email,
                         @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType
    ) {
        return loginService.loginAndUserTransform(userId, tobeSnsId, name, email, snsType);
    }

}
