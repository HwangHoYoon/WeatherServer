package com.jagiya.location.service;

import com.jagiya.location.entity.RecentLocation;
import com.jagiya.location.repository.RecentLocationRepository;
import com.jagiya.location.response.RecentLocationResponse;
import com.jagiya.user.entity.User;
import com.jagiya.user.enums.LoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecentLocationServiceImpl implements RecentLocationService{
    private final RecentLocationRepository recentLocationRepository;
    public RecentLocationResponse recentSaveLocation(User usersInfo, String cityDo, String guGun, String eupMyun, String regionCd) throws Exception {

        Optional<RecentLocation> recentLocationSelect = recentLocationRepository.findByUserTbAndRegionCd(usersInfo, regionCd);

        //만약 recentLocationSelect 값이 있다면 삭제 후 등록
        if(recentLocationSelect.isPresent()){
            recentLocationRepository.deleteById(recentLocationSelect.get().getRecentLocationId());
        }

        RecentLocation recentLocation = RecentLocation.builder()
                .userTb(usersInfo)
                .cityDo(cityDo)
                .guGun(guGun)
                .eupMyun(eupMyun)
                .regionCd(regionCd)
                .regDate(new Date())
                .build();
        recentLocationRepository.save(recentLocation);

        return RecentLocationResponse.builder()
                .cityDo(cityDo)
                .guGun(guGun)
                .eupMyun(eupMyun)
                .regionCd(regionCd)
                .build();
    }


}
