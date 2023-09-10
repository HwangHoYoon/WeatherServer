package com.jagiya.weather.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJuso is a Querydsl query type for Juso
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJuso extends EntityPathBase<Juso> {

    private static final long serialVersionUID = 389782904L;

    public static final QJuso juso = new QJuso("juso");

    public final StringPath cityDo = createString("cityDo");

    public final StringPath code = createString("code");

    public final StringPath eupMyun = createString("eupMyun");

    public final StringPath guGun = createString("guGun");

    public final NumberPath<Long> jusoId = createNumber("jusoId", Long.class);

    public final StringPath lat = createString("lat");

    public final StringPath latX = createString("latX");

    public final StringPath lon = createString("lon");

    public final StringPath lonY = createString("lonY");

    public final StringPath regDate = createString("regDate");

    public QJuso(String variable) {
        super(Juso.class, forVariable(variable));
    }

    public QJuso(Path<? extends Juso> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJuso(PathMetadata metadata) {
        super(Juso.class, metadata);
    }

}

