package com.jagiya.location.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJuso is a Querydsl query type for Juso
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJuso extends EntityPathBase<Location> {

    private static final long serialVersionUID = -2138251925L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJuso juso = new QJuso("juso");

    public final StringPath cityDo = createString("cityDo");

    public final StringPath eupMyun = createString("eupMyun");

    public final StringPath guGun = createString("guGun");

    public final QJusoGroup jusoGroup;

    public final NumberPath<Long> jusoId = createNumber("jusoId", Long.class);

    public final StringPath lat = createString("lat");

    public final StringPath latX = createString("latX");

    public final StringPath lon = createString("lon");

    public final StringPath lonY = createString("lonY");

    public final StringPath regDate = createString("regDate");

    public final StringPath regionCd = createString("regionCd");

    public QJuso(String variable) {
        this(Location.class, forVariable(variable), INITS);
    }

    public QJuso(Path<? extends Location> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJuso(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJuso(PathMetadata metadata, PathInits inits) {
        this(Location.class, metadata, inits);
    }

    public QJuso(Class<? extends Location> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jusoGroup = inits.isInitialized("jusoGroup") ? new QJusoGroup(forProperty("jusoGroup")) : null;
    }

}

