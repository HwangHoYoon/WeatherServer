package com.jagiya.location.controller;

import com.jagiya.location.response.LocationTestResponse;
import com.jagiya.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Location", description = "주소 API")
@RequestMapping("Location")
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "주소 조회", description = "주소를 입력하여 해당 주소의 법정동코드를 조회 (읍면동까지만 표시) 임시로 500개의 주소를 읍면동으로 그룹화 하기 때문에 그 이상의 결과는 제외한다.")
    @GetMapping("/getLocation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public List<LocationTestResponse> getLocation(@Schema(description = "주소명", example = "상동", name = "keyword") String keyword) throws Exception {
        return locationService.selectLocation(keyword);
    }
}
