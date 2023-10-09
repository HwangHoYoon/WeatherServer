package com.jagiya.login.service;

import com.jagiya.alarm.service.AlarmService;
import com.jagiya.common.exception.CommonException;
import com.jagiya.login.entity.User;
import com.jagiya.login.enums.LoginType;
import com.jagiya.login.repository.LoginRepository;
import com.jagiya.login.response.UserRes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
    public UserRes loginAndUserTransform(Long asisUserId, String snsId, String name, String email, Integer snsType) {

        // 새로운 계정으로 로그인
        UserRes tobeUserRes = login(snsId, name, email, snsType);
        Long tobeUserId = tobeUserRes.getUserId();
        if (tobeUserRes == null || tobeUserId == null) {
            throw new CommonException("정상적으로 로그인 되지 않았습니다.", "888");
        }

        User asisUser = usersRepository.findById(asisUserId).orElseThrow(() -> new CommonException("회원정보가 없습니다.", "222"));

        // 정상 로그인 시 기존 알람 이관
        alarmService.updateAlarmUserId(asisUserId, tobeUserId);

        // 알람 이관 성공 시 기존 유저 삭제
        usersRepository.deleteById(asisUserId);

        return tobeUserRes;
    }

}
