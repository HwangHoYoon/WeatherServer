package com.jagiya.location.repository;

import com.jagiya.location.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class LocationCustomRepositoryImpl implements LocationCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LocationGroup> selectLocationGroupByCityDo(List<String> cityDos) {
        List<Long> locationGroupIdList = jpaQueryFactory.select(QLocation.location.locationGroup.locationGroupId)
                .from(QLocation.location)
                .where(QLocation.location.cityDo.in(cityDos))
                .groupBy(QLocation.location.locationGroup.locationGroupId)
                .fetch();

        return jpaQueryFactory.selectFrom(QLocationGroup.locationGroup)
                .where(QLocationGroup.locationGroup.locationGroupId.in(locationGroupIdList))
                .fetch();
    }
}
