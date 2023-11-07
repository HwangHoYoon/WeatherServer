package com.jagiya.user.controller;

import com.jagiya.common.response.CommonMsgResponse;
import com.jagiya.common.response.MessageCode;
import com.jagiya.user.request.UserDetailUpdateRequest;
import com.jagiya.user.response.HtmlResponse;
import com.jagiya.user.response.UserDetailResponse;
import com.jagiya.user.response.UserRes;
import com.jagiya.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "로그인")
    @GetMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public UserRes login(@Schema(description = "소셜 계정 ID (비회원은 디바이스 ID)", example = "example1", name = "snsId") @NotBlank(message = "소셜계정ID를 입력해주세요.") String snsId,
                         @Schema(description = "이름", example = "홍길동", name = "name") @Nullable String name,
                         @Schema(description = "이메일 (선택 동의했을 경우)", example = "example@naver.com", name = "email") @Nullable String email,
                         @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType,
                         @Parameter(hidden = true) HttpServletResponse response
    ) {
        return userService.login(snsId, name, email, snsType, response);
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
                         @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType,
                         @Parameter(hidden = true) HttpServletResponse response
    ) {
        return userService.loginAndUserTransform(userId, tobeSnsId, name, email, snsType, response);
    }

    @Operation(summary = "회원조회", description = "회원조회")
    @GetMapping("/getUserDetail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public UserDetailResponse getUserDetail(@Schema(description = "계정 ID", example = "1", name = "userId") @NotBlank(message = "UserID를 입력해주세요.") Long userId
    ) {
        return userService.selectUserDetail(userId);
    }


    @Operation(summary = "이름 변경", description = "이름 변경")
    @PutMapping("/updateUserName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public CommonMsgResponse updateUserName(
            @RequestBody UserDetailUpdateRequest userDetailUpdateRequest
    ) {
        userService.updateUserName(userDetailUpdateRequest);
        return new CommonMsgResponse(MessageCode.SUCCESS_SAVE.getMessage());
    }

    @Operation(summary = "이용약관 조회", description = "이용약관 조회")
    @GetMapping("/getTermsOfUse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public HtmlResponse getTermsOfUse() throws IOException {
        return userService.selectTermsOfUse();
    }


    @Operation(summary = "개인정보처리방침 조회", description = "개인정보처리방침 조회")
    @GetMapping("/getPrivacyPolicy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public HtmlResponse getPrivacyPolicy() throws IOException {
        return userService.selectPrivacyPolicy();
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public CommonMsgResponse logout(@Schema(description = "계정 ID", example = "1", name = "userId") @NotBlank(message = "UserID를 입력해주세요.") Long userId,
                                    @Parameter(hidden = true) HttpServletResponse response
    ) {
        userService.logout(response, userId);
        return new CommonMsgResponse(MessageCode.SUCCESS.getMessage());
    }

}
