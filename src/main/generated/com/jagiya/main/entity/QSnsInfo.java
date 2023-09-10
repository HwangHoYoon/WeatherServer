package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnsInfo is a Querydsl query type for SnsInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnsInfo extends EntityPathBase<SnsInfo> {

    private static final long serialVersionUID = 1578509360L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnsInfo snsInfo = new QSnsInfo("snsInfo");

    public final StringPath accessToken = createString("accessToken");

    public final DateTimePath<java.util.Date> snsConnectDate = createDateTime("snsConnectDate", java.util.Date.class);

    public final NumberPath<Integer> snsInfoId = createNumber("snsInfoId", Integer.class);

    public final StringPath snsName = createString("snsName");

    public final StringPath snsProfile = createString("snsProfile");

    public final NumberPath<Integer> snsType = createNumber("snsType", Integer.class);

    public final QUsers usersTb;

    public QSnsInfo(String variable) {
        this(SnsInfo.class, forVariable(variable), INITS);
    }

    public QSnsInfo(Path<? extends SnsInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnsInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnsInfo(PathMetadata metadata, PathInits inits) {
        this(SnsInfo.class, metadata, inits);
    }

    public QSnsInfo(Class<? extends SnsInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usersTb = inits.isInitialized("usersTb") ? new QUsers(forProperty("usersTb")) : null;
    }

}

