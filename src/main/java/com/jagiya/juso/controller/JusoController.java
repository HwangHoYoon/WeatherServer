package com.jagiya.juso.controller;

import com.jagiya.juso.response.JusoTestResponse;
import com.jagiya.juso.service.JusoService;
import com.jagiya.weather.request.WeatherTestRequest;
import com.jagiya.weather.response.WeatherTestResponse;
import com.jagiya.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Juso", description = "주소 API Document")
public class JusoController {

    private final JusoService jusoService;

    @Operation(summary = "주소 조회", description = "주소 조회")
    @GetMapping("/getLocation")
    @Parameter(name = "주소 키워드", description = "주소 키워드")
    public List<JusoTestResponse> getLocation(String keyword) throws Exception{
        return jusoService.selectLocation(keyword);
    }
}
