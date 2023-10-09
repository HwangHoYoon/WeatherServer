package com.jagiya.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlarmSound is a Querydsl query type for AlarmSound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmSound extends EntityPathBase<AlarmSound> {

    private static final long serialVersionUID = 37991980L;

    public static final QAlarmSound alarmSound = new QAlarmSound("alarmSound");

    public final NumberPath<Long> alarmSoundId = createNumber("alarmSoundId", Long.class);

    public final StringPath alarmSoundName = createString("alarmSoundName");

    public final StringPath fileName = createString("fileName");

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public QAlarmSound(String variable) {
        super(AlarmSound.class, forVariable(variable));
    }

    public QAlarmSound(Path<? extends AlarmSound> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlarmSound(PathMetadata metadata) {
        super(AlarmSound.class, metadata);
    }

}

