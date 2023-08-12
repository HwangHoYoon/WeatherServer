package com.jagiya.signup.controller;

import com.jagiya.signup.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @ResponseBody
    @GetMapping("/kakao_callback")
    public void  kakaoCallback(@RequestParam String code) throws Exception {

        System.out.println("code :" + code);
        String access_Token = signUpService.getKaKaoAccessToken(code);
        signUpService.createKakaoUser(access_Token);
    }
}
