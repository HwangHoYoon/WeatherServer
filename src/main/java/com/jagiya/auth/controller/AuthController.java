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

    @Value("${oauth.kakao.url.auth}")
    private String kakaoAuthUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/getKakaoUrl")
    public String getKakaoUrl() throws Exception {
        String reqUrl = kakaoAuthUrl + "?response_type=code" + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;

        return reqUrl;
    }

    //@GetMapping("/kakao_callback")
    @GetMapping({"/kakaoCallback", "/kakaoLogin"})
    public UsersRes kakaoCallback(@RequestParam String code) throws Exception {

        System.out.println("code :" + code);
        //S
        // tring access_Token = signUpService.getKaKaoAccessToken(code);
        //signUpService.createKakaoUser(access_Token);

        return signUpService.signUp(code);
    }

}
