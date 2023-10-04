package com.jagiya.main.controller;

import com.jagiya.main.dto.member.UserRes;
import com.jagiya.main.service.inf.MemberInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "memberDelete", description = "맴버탈퇴/재가입 API")
@RequestMapping("memberInfo")
public class MemberController {

    private final MemberInfoService memberInfoService;

    @Operation(summary = "탈퇴", description = "탈퇴")
    @GetMapping("/delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public UserRes memberDelete(@Schema(description = "소셜 계정 ID (비회원은 디바이스 ID)", example = "example1", name = "snsId") @NotBlank(message = "소셜계정ID를 입력해주세요.") String snsId,
                                @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType
    ) throws Exception {
        return memberInfoService.memberDelete(snsId, snsType);
    }

}
