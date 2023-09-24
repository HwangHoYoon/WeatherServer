package com.jagiya.main.controller;

import com.jagiya.main.dto.login.AppleDTO;
import com.jagiya.main.dto.login.MsgEntity;
import com.jagiya.main.repository.UsersRepository;
import com.jagiya.main.service.Impl.AppleServiceImpl;
import com.jagiya.main.service.inf.AppleService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("apple")
public class AppleController {


    private final UsersRepository usersRepository;

    private final AppleService appleService;




    @Hidden
    @PostMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        AppleDTO appleInfo = appleService.getAppleInfo(request.getParameter("code"));

        MsgEntity result = new MsgEntity("Success", appleInfo);
        log.info("apple callback ===================> {}", result);


        //apple login 저장시작
        appleService.Save(result);
        

        return ResponseEntity.ok()
                .body(result);

    }

}
