package com.jagiya.auth.service;

import com.jagiya.auth.entity.TokenEditor;
import com.jagiya.auth.dto.UsersRes;
import com.jagiya.auth.entity.Token;
import com.jagiya.auth.entity.UsersEditor;
import com.jagiya.auth.repository.AuthTokenRepository;
import com.jagiya.auth.repository.AuthUsersRepository;
import com.jagiya.auth.dto.KakaoToken;
import com.jagiya.auth.dto.KakaoUserInfo;
import com.jagiya.main.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final AuthTokenRepository tokenRepository;
    private final AuthUsersRepository usersRepository;
    private final RestTemplate restTemplate;

    @Value("${kakao.url.auth}")
    private String kakaoAuthUrl;

    @Value("${kakao.url.token}")
    private String tokenUrl;

    @Value("${kakao.url.api}")
    private String apiUrl;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client_secret}")
    private String clientSecret;

    @Value("${kakao.grant_type}")
    private String grantType;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public String getKakaoUrl() {
        String kakaoUrl = kakaoAuthUrl + "?response_type=code" + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
        return kakaoUrl;
    }

    public UsersRes signUp(String code) {
        KakaoToken kakaoToken = requestAccessToken(code);
        KakaoUserInfo kakaoUserInfo = requestOauthInfo(kakaoToken);

        long id = kakaoUserInfo.getId();
        String email = kakaoUserInfo.getEmail();
        String name = kakaoUserInfo.getName();
        String nickname = kakaoUserInfo.getNickname();
        String profileImage = kakaoUserInfo.getProfileImage();
        Date connectedAt = kakaoUserInfo.getConnectedAt();
        String gender = kakaoUserInfo.getGender();
        Date birthDay = kakaoUserInfo.getBirthDay();
        Integer snsType = kakaoUserInfo.getOAuthProvider().getCode();

        Optional<Users> usersInfo = usersRepository.findBySnsTypeAndId(snsType, id);

        Users usersInfoRst;
        // 유저 정보가 있다면 업데이트 없으면 등록
        if (usersInfo.isPresent()) {
            usersInfoRst = usersInfo.get();
        } else {
            log.info("신규유저 등록 {}", nickname);
            Users users = Users.builder()
                    .id(id)
                    .email(email)
                    .nickname(nickname)
                    .username(name)
                    .snsType(kakaoUserInfo.getOAuthProvider().getCode())
                    .snsName(kakaoUserInfo.getOAuthProvider().getName())
                    .snsProfile(profileImage)
                    .birthday(birthDay)
                    .gender(gender)
                    .snsConnectDate(connectedAt)
                    .deleteFlag(0)
                    .agreesFalg(0)
                    .regDate(new Date())
                    .build();
            usersInfoRst = usersRepository.save(users);
        }

        Long userId = usersInfoRst.getUsersId();
        String accessToken = kakaoToken.getAccessToken();
        String refreshToken = kakaoToken.getRefreshToken();
        String scope = kakaoToken.getScope();
        Integer expiresIn = kakaoToken.getExpiresIn();
        Integer refreshTokenExpiresIn = kakaoToken.getRefreshTokenExpiresIn();
        String tokenType = kakaoToken.getTokenType();

        Optional<Token> tokenInfo = tokenRepository.findByUsersTbUsersId(userId);
        Token token;
        // 토큰 정보가 있다면 수정 없으면 등록
        if (tokenInfo.isPresent()) {
            token = tokenInfo.get();
            TokenEditor.TokenEditorBuilder tokenEditorBuilder = token.toEditor();
            TokenEditor tokenEditor = tokenEditorBuilder
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .scope(scope)
                    .expiresIn(expiresIn)
                    .refreshTokenExpiresIn(refreshTokenExpiresIn)
                    .tokenType(tokenType)
                    .build();

            token.edit(tokenEditor);
        } else {
            token = Token.builder()
                    .usersTb(usersInfoRst)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .scope(scope)
                    .expiresIn(expiresIn)
                    .refreshTokenExpiresIn(refreshTokenExpiresIn)
                    .tokenType(tokenType)
                    .regDate(new Date())
                    .build();
        }
        tokenRepository.save(token);

        return UsersRes.builder()
                .usersId(userId)
                .id(usersInfoRst.getId())
                .email(usersInfoRst.getEmail())
                .nickname(usersInfoRst.getNickname())
                .username(usersInfoRst.getUsername())
                .snsType(usersInfoRst.getSnsType())
                .snsName(usersInfoRst.getSnsName())
                .snsProfile(usersInfoRst.getSnsProfile())
                .birthday(usersInfoRst.getBirthday())
                .gender(usersInfoRst.getGender())
                .snsConnectDate(usersInfoRst.getSnsConnectDate())
                .deleteFlag(usersInfoRst.getDeleteFlag())
                .agreesFalg(usersInfoRst.getAgreesFalg())
                .regDate(usersInfoRst.getRegDate())
                .modifyDate(usersInfoRst.getModifyDate())
                .build();
    }

    public KakaoToken requestAccessToken(String code) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();;
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        //body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

        KakaoToken response = restTemplate.postForObject(tokenUrl, request, KakaoToken.class);

        // TODO 토큰 정보를 가져오지 못하면 예외발생 처리 추가
        assert response != null;
        return response;
    }

    public KakaoUserInfo requestOauthInfo(KakaoToken kakaoToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + kakaoToken.getAccessToken());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();;
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        KakaoUserInfo response = restTemplate.postForObject(apiUrl, request, KakaoUserInfo.class);

        // TODO 유저 정보를 가져오지 못하면 예외발생 처리 추가
        assert response != null;
        return response;
    }
}
