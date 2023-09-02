package com.jagiya.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jagiya.common.enums.OAuthProvider;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("connected_at")
    private Date connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;

        private String email;

        private String name;

        private String gender;

        private String birthday;

        private String birthyear;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;
    }

    public String getName() { return kakaoAccount.name; }

    public String getEmail() {
        return kakaoAccount.email;
    }

    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    public Date getBirthDay() {
        if (kakaoAccount.birthyear != null && kakaoAccount.birthday != null) {
            String birthDay = kakaoAccount.birthyear + kakaoAccount.birthday;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                return dateFormat.parse(birthDay);
            } catch (ParseException e) {
                System.out.println("카카오 날짜형식이 맞지 않습니다. : " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public String getGender() {
        return kakaoAccount.gender;
    }

    public String getProfileImage() {
        return kakaoAccount.profile.profileImage;
    }

    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
