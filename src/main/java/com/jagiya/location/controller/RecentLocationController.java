package com.jagiya.location.controller;

import com.jagiya.location.entity.RecentLocation;
import com.jagiya.location.repository.LocationRepository;
import com.jagiya.location.repository.RecentLocationRepository;
import com.jagiya.location.response.LocationResponse;
import com.jagiya.location.response.RecentLocationResponse;
import com.jagiya.location.service.LocationService;
import com.jagiya.location.service.RecentLocationService;
import com.jagiya.user.entity.User;
import com.jagiya.user.enums.LoginType;
import com.jagiya.main.dto.member.UserRes;
import com.jagiya.main.exception.MemberNotFoundException;
import com.jagiya.main.repository.UsersRepository;
import com.jagiya.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "RecentLocation", description = "최근주소 API")
@RequestMapping("location")
public class RecentLocationController {


    private final UserRepository usersRepository;
    private final RecentLocationService recentLocationService;
    private final RecentLocationRepository recentLocationRepository;


    @Operation(summary = "검색 주소 저장", description = "검색한 주소를 저장한다")
    @GetMapping("/getRecentLocation")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    public RecentLocationResponse getRecentLocation(
            @Schema(description = "소셜 계정 ID (비회원은 디바이스 ID)", example = "example1", name = "snsId") @NotBlank(message = "소셜계정ID를 입력해주세요.") String snsId,
            @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType,
            @Schema(description = "시/도", example = "경상남도", name = "cityDo") String cityDo,
            @Schema(description = "구/군", example = "거제시", name = "guGun") String guGun,
            @Schema(description = "읍면동", example = "상동동", name = "eupMyun") String eupMyun,
            @Schema(description = "법정동코드", example = "4831011000", name = "regionCd") String regionCd
    ) throws Exception {


        User usersInfo = usersRepository.findBySnsTypeAndSnsId(snsType, snsId).orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

        //만약 최근 주소 검색에서 가져온 주소일 경우 기존데이터 삭제후 재등록
        return recentLocationService.recentSaveLocation(usersInfo, cityDo, guGun, eupMyun, regionCd);




    }


    @Operation(summary = "최근검색 주소목록", description = "최근 검색한 주소목록")
    @GetMapping("/getRecentSelectLocation")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    public List<RecentLocation> getRecentSelectLocation(
            @Schema(description = "소셜 계정 ID (비회원은 디바이스 ID)", example = "example1", name = "snsId") @NotBlank(message = "소셜계정ID를 입력해주세요.") String snsId,
            @Schema(description = "소셜 타입(0 비회원, 1 카카오, 2 애플)", example = "0", name = "snsType") @NotBlank(message = "소셜타입을 입력해주세요.") Integer snsType
    ) throws Exception {

        User usersInfo = usersRepository.findBySnsTypeAndSnsId(snsType, snsId).orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));
        return recentLocationRepository.findTop10ByUserTbOrderByRegDateDesc(usersInfo);


    }



    @Operation(summary = "최근검색 주소삭제", description = "최근 검색한 주소삭제")
    @GetMapping("/getRecentDeleteLocation")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    public void getRecentDeleteLocation(
            @Schema(description = "recentLocationId", example = "6", name = "recentLocationId") Long recentLocationId
    ) throws Exception {

        recentLocationRepository.deleteById(recentLocationId);

    }











}
