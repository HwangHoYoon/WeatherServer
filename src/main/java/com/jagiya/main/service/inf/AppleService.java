package com.jagiya.main.service.inf;

import com.jagiya.main.dto.login.AppleDTO;
import com.jagiya.main.dto.login.ApplePublicKeys;
import com.jagiya.main.dto.login.MsgEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

public interface AppleService {


    String getAppleLogin();

    AppleDTO getAppleInfo(String code) throws  Exception;

    void Save(MsgEntity msgEntity) throws Exception;



}
