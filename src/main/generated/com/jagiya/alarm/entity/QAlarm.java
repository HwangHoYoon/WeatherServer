package com.jagiya.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = -42856285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final NumberPath<Long> alarmId = createNumber("alarmId", Long.class);

    public final QAlarmSound alarmSound;

    public final StringPath alarmTime = createString("alarmTime");

    public final NumberPath<Integer> enabled = createNumber("enabled", Integer.class);

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath reminder = createString("reminder");

    public final com.jagiya.login.entity.QUser user;

    public final NumberPath<Integer> vibration = createNumber("vibration", Integer.class);

    public final NumberPath<Integer> volume = createNumber("volume", Integer.class);

    public QAlarm(String variable) {
        this(Alarm.class, forVariable(variable), INITS);
    }

    public QAlarm(Path<? extends Alarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarm(PathMetadata metadata, PathInits inits) {
        this(Alarm.class, metadata, inits);
    }

    public QAlarm(Class<? extends Alarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarmSound = inits.isInitialized("alarmSound") ? new QAlarmSound(forProperty("alarmSound")) : null;
        this.user = inits.isInitialized("user") ? new com.jagiya.login.entity.QUser(forProperty("user")) : null;
    }

}

