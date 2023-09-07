package com.jagiya.main.repository;

import com.jagiya.main.entity.Juso;
import com.jagiya.main.entity.QJuso;
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
    public Juso selectJusoWhereCode(String code) {
        return jpaQueryFactory.selectFrom(QJuso.juso)
                .where(QJuso.juso.code.eq(code))
                .fetchOne();
    }
}
