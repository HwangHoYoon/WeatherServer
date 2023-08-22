package com.jagiya.auth.controller;

import com.jagiya.auth.dto.UsersRes;
import com.jagiya.auth.entity.Users;
import com.jagiya.main.entity.SnsInfo;
import com.jagiya.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService signUpService;

    @Value("${oauth.kakao.url.auth}")
    private String kakaoAuthUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/auth/getKakaoUrl")
    public String getKakaoUrl() throws Exception {
        String reqUrl = kakaoAuthUrl + "?response_type=code" + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;

        return reqUrl;
    }

    //@GetMapping("/kakao_callback")
    @GetMapping({"/auth/kakaoCallback", "/auth/kakaoLogin"})
    public String kakaoCallback(@RequestParam String code) throws Exception {

        System.out.println("code :" + code);
        //String access_Token = signUpService.getKaKaoAccessToken(code);
        //signUpService.createKakaoUser(access_Token);

        UsersRes usersRes = signUpService.signUp(code);

        return usersRes.toString();
    }

}
