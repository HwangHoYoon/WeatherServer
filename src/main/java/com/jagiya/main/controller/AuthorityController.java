package com.jagiya.main.controller;

import com.jagiya.main.dto.member.UserRes;
import com.jagiya.main.entity.Authority;
import com.jagiya.main.exception.MemberNotFoundException;
import com.jagiya.main.repository.AuthorityRepository;
import com.jagiya.main.service.inf.AuthorityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Authority", description = "알림권한 API")
@RequestMapping("AuthorityInfo")
public class AuthorityController {

    private final AuthorityService authorityService;

    private final AuthorityRepository authorityRepository;

    @Operation(summary = "알림권한등록", description = "알림권한등록")
    @GetMapping("/alarmAuthorityInsert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public Authority alarmAuthorityInsert(@Schema(description = "디바이스 ID", example = "test1234", name = "deviceId") @NotBlank(message = "디바이스ID를 입력해주세요") String deviceId,
                                          @Schema(description = "알람권한 동의여부", example = "0", name = "authFlag") @NotBlank(message = "알람권한 동의여부(0:허용, 1:허용안함)") Integer authFlag
    ) throws Exception {
        return authorityService.alarmAuthorityInsert(deviceId, authFlag);
    }





    @Operation(summary = "알림권한조회", description = "알림권한조회")
    @GetMapping("/alarmAuthoritySelect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    }
    )
    public Authority alarmAuthorityInsert(@Schema(description = "디바이스 ID", example = "test1234", name = "deviceId") @NotBlank(message = "디바이스ID를 입력해주세요") String deviceId
    ) throws Exception {

        Authority authority = authorityRepository.findByDeviceId(deviceId).orElseThrow(() -> new MemberNotFoundException("디바이스 권한 미설정"));

        return authority;
    }




}
