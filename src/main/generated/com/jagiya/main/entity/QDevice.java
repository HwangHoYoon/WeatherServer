package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDevice is a Querydsl query type for Device
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDevice extends EntityPathBase<Device> {

    private static final long serialVersionUID = 1552953132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDevice device = new QDevice("device");

    public final DateTimePath<java.util.Date> authAlerDate = createDateTime("authAlerDate", java.util.Date.class);

    public final NumberPath<Integer> authAlert = createNumber("authAlert", Integer.class);

    public final NumberPath<Integer> authLocation = createNumber("authLocation", Integer.class);

    public final DateTimePath<java.util.Date> authLocationDate = createDateTime("authLocationDate", java.util.Date.class);

    public final NumberPath<Integer> authShow = createNumber("authShow", Integer.class);

    public final DateTimePath<java.util.Date> authShowDate = createDateTime("authShowDate", java.util.Date.class);

    public final NumberPath<Integer> authStart = createNumber("authStart", Integer.class);

    public final DateTimePath<java.util.Date> authStartDate = createDateTime("authStartDate", java.util.Date.class);

    public final StringPath deviceCode = createString("deviceCode");

    public final NumberPath<Integer> deviceId = createNumber("deviceId", Integer.class);

    public final StringPath deviceName = createString("deviceName");

    public final QUsers usersTb;

    public QDevice(String variable) {
        this(Device.class, forVariable(variable), INITS);
    }

    public QDevice(Path<? extends Device> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDevice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDevice(PathMetadata metadata, PathInits inits) {
        this(Device.class, metadata, inits);
    }

    public QDevice(Class<? extends Device> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usersTb = inits.isInitialized("usersTb") ? new QUsers(forProperty("usersTb")) : null;
    }

}

