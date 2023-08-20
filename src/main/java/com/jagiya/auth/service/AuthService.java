package com.jagiya.auth.service;

import com.jagiya.auth.dto.TokenEditor;
import com.jagiya.auth.dto.UsersRes;
import com.jagiya.auth.entity.Token;
import com.jagiya.auth.entity.Users;
import com.jagiya.auth.repository.AuthTokenRepository;
import com.jagiya.common.enums.OAuthProvider;
import com.jagiya.auth.repository.AuthSnsInfoRepository;
import com.jagiya.auth.repository.AuthUsersRepository;
import com.jagiya.auth.dto.KakaoToken;
import com.jagiya.auth.dto.KakaoUserInfo;
import com.jagiya.main.entity.SnsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthTokenRepository tokenRepository;
    private final AuthUsersRepository usersRepository;
    private final RestTemplate restTemplate;

    @Value("${oauth.kakao.url.token}")
    private String tokenUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.client_secret}")
    private String clientSecret;

    @Value("${oauth.kakao.grant_type}")
    private String grantType;

    @Value("${oauth.kakao.redirect_uri}")
    private String redirectUri;

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

        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("email : " + email);
        System.out.println("nickname : " + nickname);
        System.out.println("connected_at : " + connectedAt);
        System.out.println("profileImage : " + profileImage);
        System.out.println("birthDay : " + kakaoUserInfo.getBirthDay());
        System.out.println("gender : " + gender);

        //TODO CI, CI_DATE, UUID 추가 필요

        Users usersInfoRst = usersRepository.findBySnsTypeAndId(OAuthProvider.KAKAO.getCode(), id)
                .orElseGet(() -> {
                    Users users = Users.builder()
                            .id(id)
                            .email(email)
                            .nickname(nickname)
                            .username(name)
                            .snsType(OAuthProvider.KAKAO.getCode())
                            .snsName(OAuthProvider.KAKAO.getName())
                            .snsProfile(profileImage)
                            .birthday(birthDay)
                            .gender(gender)
                            .snsConnectDate(connectedAt)
                            .deleteFlag(0)
                            .agreesFalg(0)
                            .regDate(new Date())
                            .build();
                    Users usersRst = usersRepository.save(users);
                    return usersRst;
                });
        Long userId = usersInfoRst.getUsersId();
        System.out.println("userId : " + userId);


        String accessToken = kakaoToken.getAccessToken();
        String refreshToken = kakaoToken.getRefreshToken();
        String scope = kakaoToken.getScope();
        Integer expiresIn = kakaoToken.getExpiresIn();
        Integer refreshTokenExpiresIn = kakaoToken.getRefreshTokenExpiresIn();
        String tokenType = kakaoToken.getTokenType();


        Optional<Token> token = tokenRepository.findByUserId(userId);

        if (token.isPresent()) {
            Token updateToken = token.get();
            TokenEditor.TokenEditorBuilder tokenEditorBuilder = updateToken.toEditor();

            TokenEditor tokenEditor = tokenEditorBuilder
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .scope(scope)
                    .expiresIn(expiresIn)
                    .refreshTokenExpiresIn(refreshTokenExpiresIn)
                    .tokenType(tokenType)
                    .build();

            updateToken.edit(tokenEditor);
            tokenRepository.save(updateToken);
        } else {
            Token insertToken = Token.builder()
                    .usersTb(usersInfoRst)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .scope(scope)
                    .expiresIn(expiresIn)
                    .refreshTokenExpiresIn(refreshTokenExpiresIn)
                    .tokenType(tokenType)
                    .regDate(new Date())
                    .build();
            tokenRepository.save(insertToken);
        }

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
                .build();
    }

    public KakaoToken requestAccessToken(String code) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();;
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
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
