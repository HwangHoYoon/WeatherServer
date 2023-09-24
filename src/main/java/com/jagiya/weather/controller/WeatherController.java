package com.jagiya.weather.controller;

import com.jagiya.common.response.CommonResponse;
import com.jagiya.weather.entity.Weather;
import com.jagiya.weather.request.WeatherTestRequest;
import com.jagiya.weather.response.WeatherTestResponse;
import com.jagiya.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("weather")
@Tag(name = "Weather", description = "날씨 API")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/getLocationForWeather")
    @Operation(summary = "지역별 날씨", description = "지역코드와 기준날짜를 입력하면 날씨DB를 조회(지역코드는 /location/getLocation에서 조회가능하다.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public List<WeatherTestResponse> getLocationForWeather(@Schema(description = "주소코드", example = "1111010100", name = "regionCd") String regionCd, @Schema(description = "기준날짜", example = "20230914", name = "fcstDate") String fcstDate) throws Exception {
        return weatherService.selectLocationForWeather(regionCd, fcstDate);
    }

    @GetMapping("/refreshLocationForWeather")
    @Operation(summary = "지역별 날씨갱신", description = "지역코드와 갱신타입을 입력하면 해당 지역의 날씨를 갱신하여 DB를 저장하고 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
        }
    )
    public List<WeatherTestResponse> refreshLocationForWeather(@Schema(description = "주소코드", example = "1111010100", name = "regionCd") String regionCd, @Schema(description = "갱신타입(0:초단기예보, 1:단기예보)", example = "0", name = "refreshType") String refreshType) throws Exception {
        return weatherService.refreshLocationForWeather(regionCd, refreshType);
    }

}
