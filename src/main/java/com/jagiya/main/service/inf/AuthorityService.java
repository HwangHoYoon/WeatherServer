package com.jagiya.main.service.inf;

import com.jagiya.main.entity.Authority;

public interface AuthorityService {

    Authority alarmAuthorityInsert(String deviceId, Integer authFlag)throws Exception;

}
