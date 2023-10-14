package com.jagiya.main.service.Impl;

import com.jagiya.main.dto.member.UserRes;
import com.jagiya.main.entity.Authority;
import com.jagiya.main.repository.AuthorityRepository;
import com.jagiya.main.service.inf.AuthorityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    //디바이스 등록수정
    @Transactional
    public Authority alarmAuthorityInsert(String deviceId, Integer authFlag)throws Exception{

        Optional<Authority> authority = authorityRepository.findByDeviceId(deviceId);
        Authority authorityDto = null;

        log.info("authority =========> {}", authority );

        if(authority.isPresent()){
            log.info("수정 authority =========> ");
            //수정
            authority.get().setDeviceId(authority.get().getDeviceId());
            authority.get().setAuthFlag(authFlag);
            authority.get().setAuthDate(new Date());

            authorityDto = authorityRepository.save(authority.get());

        }else {
            log.info("등록 authority =========> ");
            //등록
            authorityDto = Authority.builder()
                    .deviceId(deviceId)
                    .authFlag(authFlag)
                    .authDate(new Date())
                    .build();

            authorityRepository.save(authorityDto);
        }

        return authorityDto;
    }


}
