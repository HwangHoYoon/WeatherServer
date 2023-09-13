package com.jagiya.juso.repository;

import com.jagiya.juso.entity.Juso;
import com.jagiya.juso.entity.JusoGroup;
import com.jagiya.juso.entity.QJuso;
import com.jagiya.juso.entity.QJusoGroup;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JusoCustomRepositoryImpl implements JusoCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Juso> selectJusoGroupByLocation() {
        return jpaQueryFactory.selectFrom(QJuso.juso)
                .groupBy(QJuso.juso.latX, QJuso.juso.lonY)
                .fetch();
    }

    @Override
    public List<JusoGroup> selectJusoGroupByCityDo(List<String> cityDos) {
        List<Long> jusoGroupIdList = jpaQueryFactory.select(QJuso.juso.jusoGroup.jusoGroupId)
                .from(QJuso.juso)
                .where(QJuso.juso.cityDo.in(cityDos))
                .groupBy(QJuso.juso.jusoGroup.jusoGroupId)
                .fetch();

        return jpaQueryFactory.selectFrom(QJusoGroup.jusoGroup)
                .where(QJusoGroup.jusoGroup.jusoGroupId.in(jusoGroupIdList))
                .fetch();
    }
}
