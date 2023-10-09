package com.jagiya.main.service.Impl;

import com.jagiya.user.entity.User;
import com.jagiya.user.enums.LoginType;
import com.jagiya.user.repository.UserRepository;
import com.jagiya.main.dto.member.UserRes;
import com.jagiya.main.exception.MemberNotFoundException;
import com.jagiya.main.service.inf.MemberInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberInfoServiceImpl implements MemberInfoService {

    private final UserRepository usersRepository;

    @Transactional
    public UserRes memberDelete(String snsId, Integer snsType)throws Exception{



            User usersInfo = usersRepository.findBySnsTypeAndSnsId(snsType, snsId).orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

            usersInfo.setDeleteFlag(1);
            usersInfo.setDeleteDate(new Date());
            usersInfo.setModifyDate(new Date());


            return UserRes.builder()
                    .userId(usersInfo.getUserId())
                    .snsId(usersInfo.getSnsId())
                    .email(usersInfo.getEmail())
                    .name(usersInfo.getName())
                    .snsName(LoginType.getLoginTypeName(usersInfo.getSnsType()))
                    .deleteFlag(usersInfo.getDeleteFlag())
                    .deleteDate(usersInfo.getDeleteDate())
                    .build();


    }

}
