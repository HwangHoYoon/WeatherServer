package com.jagiya.main.service.Impl;

import com.jagiya.common.filter.CustomRequestWrapper;
import com.jagiya.login.entity.User;
import com.jagiya.login.enums.LoginType;
import com.jagiya.login.repository.LoginRepository;
import com.jagiya.main.dto.member.UserRes;
import com.jagiya.main.service.inf.MemberInfoService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberInfoServiceImpl implements MemberInfoService {

    private final LoginRepository usersRepository;
    public UserRes memberDelete(String snsId, Integer snsType) {

        Optional<User> usersInfo = Optional.ofNullable(usersRepository.findBySnsTypeAndSnsId(snsType, snsId).orElseThrow(() -> new NullPointerException("회원이 존재하지 않습니다.")));

        User usersInfoRst;

        User user = User.builder()
                .snsId(usersInfo.get().getSnsId())
                .deleteFlag(1)
                .deleteDate(new Date())
                .build();
            usersInfoRst = usersRepository.save(user);

        return UserRes.builder()
                .userId(usersInfoRst.getUserId())
                .snsId(usersInfoRst.getSnsId())
                .email(usersInfoRst.getEmail())
                .name(usersInfoRst.getName())
                .snsName(LoginType.getLoginTypeName(usersInfoRst.getSnsType()))
                .deleteFlag(user.getDeleteFlag())
                .deleteDate(user.getDeleteDate())
                .build();
    }

}
