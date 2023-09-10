package com.jagiya.juso.repository;

import com.jagiya.juso.entity.Juso;
import com.jagiya.juso.entity.QJuso;
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
}
