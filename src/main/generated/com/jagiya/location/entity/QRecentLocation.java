package com.jagiya.location.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentLocation is a Querydsl query type for RecentLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentLocation extends EntityPathBase<RecentLocation> {

    private static final long serialVersionUID = -2031063294L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentLocation recentLocation = new QRecentLocation("recentLocation");

    public final StringPath cityDo = createString("cityDo");

    public final StringPath eupMyun = createString("eupMyun");

    public final StringPath guGun = createString("guGun");

    public final NumberPath<Long> recentLocationId = createNumber("recentLocationId", Long.class);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath regionCd = createString("regionCd");

    public final com.jagiya.user.entity.QUser userTb;

    public QRecentLocation(String variable) {
        this(RecentLocation.class, forVariable(variable), INITS);
    }

    public QRecentLocation(Path<? extends RecentLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentLocation(PathMetadata metadata, PathInits inits) {
        this(RecentLocation.class, metadata, inits);
    }

    public QRecentLocation(Class<? extends RecentLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userTb = inits.isInitialized("userTb") ? new com.jagiya.user.entity.QUser(forProperty("userTb")) : null;
    }

}

