package com.jagiya.login.service;

import com.jagiya.login.entity.TokenEditor;
import com.jagiya.login.dto.UsersRes;
import com.jagiya.login.entity.Token;
import com.jagiya.login.entity.User;
import com.jagiya.login.repository.AuthTokenRepository;
import com.jagiya.login.repository.LoginRepository;
import com.jagiya.login.dto.KakaoToken;
import com.jagiya.login.dto.KakaoUserInfo;
import com.jagiya.login.enums.LoginType;
import com.jagiya.login.response.UserRes;
import com.jagiya.main.entity.Users;
import com.jagiya.main.exception.MemberNotFoundException;
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
public class LoginService {

    private final LoginRepository usersRepository;

    public UserRes login(String snsId, String name, String email, Integer snsType) {
        Optional<User> usersInfo = usersRepository.findBySnsTypeAndSnsId(snsType, snsId);

        User usersInfoRst;
        // 유저 정보가 있다면 업데이트 없으면 등록
        if (usersInfo.isPresent()) {

            //탈퇴 유저일때 정상화
            if(usersInfo.get().getDeleteFlag() == 1){
                log.info("탈퇴유저 확인 {}", usersInfo.get());
                usersInfo.get().setDeleteFlag(0);
                usersInfo.get().setDeleteDate(null);
                usersInfo.get().setModifyDate(new Date());
                log.info(" 재가입 확인 {}", usersInfo.get());
                usersRepository.save(usersInfo.get());
            }

            log.info("기존유저 조회 {}", name);
            usersInfoRst = usersInfo.get();

        } else {
            log.info("신규유저 등록 {}", name);
            User user = User.builder()
                    .snsId(snsId)
                    .email(email)
                    .name(name)
                    .snsType(snsType)
                    .regDate(new Date())
                    .build();
            usersInfoRst = usersRepository.save(user);
        }

        return UserRes.builder()
                .userId(usersInfoRst.getUserId())
                .snsId(usersInfoRst.getSnsId())
                .email(usersInfoRst.getEmail())
                .name(usersInfoRst.getName())
                .snsName(LoginType.getLoginTypeName(usersInfoRst.getSnsType()))
                .build();
    }

}
