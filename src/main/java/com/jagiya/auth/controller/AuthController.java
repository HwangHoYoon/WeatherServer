package com.jagiya.auth.controller;

import com.jagiya.auth.dto.UsersRes;
import com.jagiya.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(name = "auth", description = "소셜 로그인")
public class AuthController {

    private final AuthService signUpService;

    @GetMapping("/getKakaoUrl")
    public String getKakaoUrl() throws Exception {
        return signUpService.getKakaoUrl();
    }

    //@GetMapping("/kakao_callback")
    @GetMapping({"/kakaoCallback", "/kakaoLogin"})
    public UsersRes kakaoCallback(@RequestParam String code) throws Exception {
        return signUpService.signUp(code);
    }

}
