package com.jagiya.main.controller;

import com.jagiya.main.entity.TestReq;
import com.jagiya.main.entity.TestRes;
import com.jagiya.main.service.TestService;
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
        log.info("test main controller");
        if (testReq.getId() == null) {
            testReq.setId(1L);
        }
        TestRes testRes = testService.findById(testReq.getId());
        return testRes.getName();
    }
}
