package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLog is a Querydsl query type for UserLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserLog extends EntityPathBase<UserLog> {

    private static final long serialVersionUID = -810045085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLog userLog = new QUserLog("userLog");

    public final DateTimePath<java.util.Date> loginDate = createDateTime("loginDate", java.util.Date.class);

    public final NumberPath<Integer> loginStatus = createNumber("loginStatus", Integer.class);

    public final NumberPath<Integer> userLogId = createNumber("userLogId", Integer.class);

    public final QUsers usersTb;

    public QUserLog(String variable) {
        this(UserLog.class, forVariable(variable), INITS);
    }

    public QUserLog(Path<? extends UserLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLog(PathMetadata metadata, PathInits inits) {
        this(UserLog.class, metadata, inits);
    }

    public QUserLog(Class<? extends UserLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usersTb = inits.isInitialized("usersTb") ? new QUsers(forProperty("usersTb")) : null;
    }

}

