package com.jagiya.main.controller;

import com.jagiya.main.dto.login.AppleDTO;
import com.jagiya.main.dto.login.MsgEntity;
import com.jagiya.main.service.Impl.AppleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("apple")
public class AppleController {

    private final AppleService appleService;

    @PostMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        AppleDTO appleInfo = appleService.getAppleInfo(request.getParameter("code"));

        MsgEntity result = new MsgEntity("Success", appleInfo);
        log.info("apple callback ===================> {}", result);


        

        return ResponseEntity.ok()
                .body(result);

    }

}
