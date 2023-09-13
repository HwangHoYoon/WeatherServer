package com.jagiya.juso.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJusoGroup is a Querydsl query type for JusoGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJusoGroup extends EntityPathBase<JusoGroup> {

    private static final long serialVersionUID = -794176556L;

    public static final QJusoGroup jusoGroup = new QJusoGroup("jusoGroup");

    public final NumberPath<Long> jusoGroupId = createNumber("jusoGroupId", Long.class);

    public final StringPath latX = createString("latX");

    public final StringPath lonY = createString("lonY");

    public final StringPath regDate = createString("regDate");

    public QJusoGroup(String variable) {
        super(JusoGroup.class, forVariable(variable));
    }

    public QJusoGroup(Path<? extends JusoGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJusoGroup(PathMetadata metadata) {
        super(JusoGroup.class, metadata);
    }

}

