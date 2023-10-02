package com.jagiya.location.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationGroup is a Querydsl query type for LocationGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationGroup extends EntityPathBase<LocationGroup> {

    private static final long serialVersionUID = -1331298888L;

    public static final QLocationGroup locationGroup = new QLocationGroup("locationGroup");

    public final StringPath latX = createString("latX");

    public final NumberPath<Long> locationGroupId = createNumber("locationGroupId", Long.class);

    public final StringPath lonY = createString("lonY");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public QLocationGroup(String variable) {
        super(LocationGroup.class, forVariable(variable));
    }

    public QLocationGroup(Path<? extends LocationGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationGroup(PathMetadata metadata) {
        super(LocationGroup.class, metadata);
    }

}

