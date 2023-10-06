package com.jagiya.login.service;

import com.jagiya.alarm.service.AlarmService;
import com.jagiya.common.exception.CommonException;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final AlarmService alarmService;


    public UserRes login(String snsId, String name, String email, Integer snsType) {
        Optional<User> usersInfo = usersRepository.findBySnsTypeAndSnsId(snsType, snsId);

        User usersInfoRst;
        // 유저 정보가 있다면 업데이트 없으면 등록
        if (usersInfo.isPresent()) {
            log.info("기존유저 조회 {}", name);
            usersInfoRst = usersInfo.get();
        } else {
            // 비회원은 이름 비회원이라고 저장
            if (snsType == 0) {
                name = "비회원";
            } else {
                if (StringUtils.isBlank(name)) {
                    throw new CommonException("이름정보가 없습니다.", "666");
                }
            }

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

    @Transactional
    public UserRes loginAndUserTransform(String asisSnsId, String tobeSnsId, String name, String email, Integer snsType) {
        // 새로운 계정으로 로그인
        UserRes userRes = login(tobeSnsId, name, email, snsType);
        Long tobeUserId = userRes.getUserId();
        if (userRes == null || tobeUserId == null) {
            throw new CommonException("정상적으로 로그인 되지 않았습니다.", "888");
        }

        // 기존 유저 조회
        
        // 정상 로그인 시 기존 알람 이관
        alarmService.updateAlarmUserId(1L, tobeUserId);
        
        // 알람 이관 성공 시 기존 유저 삭제
        

        return userRes;
    }
}
