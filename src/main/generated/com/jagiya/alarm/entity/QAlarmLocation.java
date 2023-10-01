package com.jagiya.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmLocation is a Querydsl query type for AlarmLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmLocation extends EntityPathBase<AlarmLocation> {

    private static final long serialVersionUID = -1893809800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmLocation alarmLocation = new QAlarmLocation("alarmLocation");

    public final QAlarm alarm;

    public final NumberPath<Long> alarmLocationId = createNumber("alarmLocationId", Long.class);

    public final com.jagiya.location.entity.QLocation location;

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public QAlarmLocation(String variable) {
        this(AlarmLocation.class, forVariable(variable), INITS);
    }

    public QAlarmLocation(Path<? extends AlarmLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmLocation(PathMetadata metadata, PathInits inits) {
        this(AlarmLocation.class, metadata, inits);
    }

    public QAlarmLocation(Class<? extends AlarmLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new QAlarm(forProperty("alarm"), inits.get("alarm")) : null;
        this.location = inits.isInitialized("location") ? new com.jagiya.location.entity.QLocation(forProperty("location"), inits.get("location")) : null;
    }

}

