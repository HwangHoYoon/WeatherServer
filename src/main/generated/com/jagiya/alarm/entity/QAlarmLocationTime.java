package com.jagiya.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmLocationTime is a Querydsl query type for AlarmLocationTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmLocationTime extends EntityPathBase<AlarmLocationTime> {

    private static final long serialVersionUID = 1989741669L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmLocationTime alarmLocationTime = new QAlarmLocationTime("alarmLocationTime");

    public final QAlarmLocation alarmLocation;

    public final NumberPath<Long> alarmLocationTimeId = createNumber("alarmLocationTimeId", Long.class);

    public final StringPath locationTime = createString("locationTime");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public QAlarmLocationTime(String variable) {
        this(AlarmLocationTime.class, forVariable(variable), INITS);
    }

    public QAlarmLocationTime(Path<? extends AlarmLocationTime> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmLocationTime(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmLocationTime(PathMetadata metadata, PathInits inits) {
        this(AlarmLocationTime.class, metadata, inits);
    }

    public QAlarmLocationTime(Class<? extends AlarmLocationTime> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarmLocation = inits.isInitialized("alarmLocation") ? new QAlarmLocation(forProperty("alarmLocation"), inits.get("alarmLocation")) : null;
    }

}

