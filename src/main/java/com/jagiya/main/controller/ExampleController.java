package com.jagiya.main.controller;

import com.jagiya.main.dto.TestReq;
import com.jagiya.main.dto.TestRes;
import com.jagiya.main.entity.Device;
import com.jagiya.main.entity.Qna;
import com.jagiya.main.entity.SnsInfo;
import com.jagiya.main.service.Impl.TestService;
//import io.swagger.annotations.Tag;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Template", description = "템플릿 API Document")
public class ExampleController {

    private final TestService testService;

    @Hidden
    @Operation(summary = "SnsInfo/QNA 테스트", description = "SnsInfo/QNA 파라미터 값 test")
    @Parameter(name = "SnsInfo 파라미터", description = "SnsInfo 설명설명")
    @PostMapping("/snsTest")
    public Qna returnStr(SnsInfo str) {
        Qna qna = null;
        return qna ;
    }

    @Hidden
    @Operation(summary = "SDevice 테스트", description = "Device test 설명하는란....")
    @GetMapping("/Device")
    @Parameter(name = "Device 파라미터", description = "Device 설명설명")
    public String example(Device device) {
        return "Device 예시 API";
    }

    @Hidden
    @GetMapping("/")
    public String ignore() {
        return "무시되는 API";
    }



}