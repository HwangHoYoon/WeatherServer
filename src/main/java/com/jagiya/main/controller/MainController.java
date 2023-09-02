package com.jagiya.main.controller;

import com.jagiya.main.dto.TestReq;
import com.jagiya.main.dto.TestRes;
import com.jagiya.main.service.Impl.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Template", description = "템플릿 API Document")
public class MainController {

    private final TestService testService;

    @Operation(summary = "test 조회 API", description = "test id를 조회합니다.")
    @GetMapping("/test1")
    @Parameter(name = "TestReq 파라미터", description = "TestReq 설명설명")
    public TestRes test(TestReq testReq) {
        log.info("main controller test");
        if (testReq.getId() == null) {
            testReq.setId(1L);
        }
        TestRes testRes = testService.findById(testReq.getId());
        return testRes;
    }

}
