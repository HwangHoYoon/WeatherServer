package com.jagiya.location.service;

import com.jagiya.location.response.RecentLocationResponse;
import com.jagiya.user.entity.User;

import java.util.List;

public interface RecentLocationService {

    RecentLocationResponse recentSaveLocation(User usersInfo, String cityDo, String guGun, String eupMyun, String regionCd) throws Exception;

    List<RecentLocationResponse> selectRecentSelectLocation(Long userId);
}
