package com.jagiya.location.service;

import com.jagiya.location.entity.RecentLocation;
import com.jagiya.location.repository.RecentLocationRepository;
import com.jagiya.location.response.RecentLocationResponse;
import com.jagiya.main.exception.MemberNotFoundException;
import com.jagiya.user.entity.User;
import com.jagiya.user.enums.LoginType;
import com.jagiya.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecentLocationServiceImpl implements RecentLocationService{
    private final RecentLocationRepository recentLocationRepository;

    private final UserRepository usersRepository;

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

        RecentLocation recentLocationRst = recentLocationRepository.save(recentLocation);

        return RecentLocationResponse.builder()
                .cityDo(cityDo)
                .guGun(guGun)
                .eupMyun(eupMyun)
                .regionCd(regionCd)
                .recentLocationId(recentLocationRst.getRecentLocationId())
                .build();
    }

    @Override
    public List<RecentLocationResponse> selectRecentSelectLocation(Long userId) {
        User usersInfo = usersRepository.findById(userId).orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));
        List<RecentLocation> recentLocationList = recentLocationRepository.findTop10ByUserTbOrderByRegDateDesc(usersInfo);

        List<RecentLocationResponse> recentLocationResponses = new ArrayList<>();

        recentLocationList.stream().forEach(recentLocation -> {
            RecentLocationResponse recentLocationResponse = RecentLocationResponse.builder()
                    .cityDo(recentLocation.getCityDo())
                    .eupMyun(recentLocation.getEupMyun())
                    .guGun(recentLocation.getGuGun())
                    .regionCd(recentLocation.getRegionCd())
                    .recentLocationId(recentLocation.getRecentLocationId())
                    .build();
            recentLocationResponses.add(recentLocationResponse);
        });

        return recentLocationResponses;
    }

}
