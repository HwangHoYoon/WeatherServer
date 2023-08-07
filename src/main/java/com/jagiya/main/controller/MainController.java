package com.jagiya.main.controller;

import com.jagiya.main.dto.TestReq;
import com.jagiya.main.dto.TestRes;
import com.jagiya.main.service.Impl.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final TestService testService;

    @GetMapping
    public String main(TestReq testReq) {
        log.info("main controller");
        if (testReq.getId() == null) {
            testReq.setId(1L);
        }
        TestRes testRes = testService.findById(testReq.getId());
        return testRes.getName();
    }
}
