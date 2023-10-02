package com.jagiya.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmWeek is a Querydsl query type for AlarmWeek
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmWeek extends EntityPathBase<AlarmWeek> {

    private static final long serialVersionUID = -552854729L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmWeek alarmWeek = new QAlarmWeek("alarmWeek");

    public final QAlarm alarm;

    public final NumberPath<Long> alarmWeekId = createNumber("alarmWeekId", Long.class);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final QWeek week;

    public QAlarmWeek(String variable) {
        this(AlarmWeek.class, forVariable(variable), INITS);
    }

    public QAlarmWeek(Path<? extends AlarmWeek> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmWeek(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmWeek(PathMetadata metadata, PathInits inits) {
        this(AlarmWeek.class, metadata, inits);
    }

    public QAlarmWeek(Class<? extends AlarmWeek> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new QAlarm(forProperty("alarm"), inits.get("alarm")) : null;
        this.week = inits.isInitialized("week") ? new QWeek(forProperty("week")) : null;
    }

}

