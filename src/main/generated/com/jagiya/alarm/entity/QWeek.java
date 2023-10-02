package com.jagiya.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeek is a Querydsl query type for Week
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeek extends EntityPathBase<Week> {

    private static final long serialVersionUID = -1801848990L;

    public static final QWeek week = new QWeek("week");

    public final StringPath dayName = createString("dayName");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final NumberPath<Long> weekId = createNumber("weekId", Long.class);

    public QWeek(String variable) {
        super(Week.class, forVariable(variable));
    }

    public QWeek(Path<? extends Week> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeek(PathMetadata metadata) {
        super(Week.class, metadata);
    }

}

