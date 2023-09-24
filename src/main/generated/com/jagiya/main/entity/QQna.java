package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQna is a Querydsl query type for Qna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQna extends EntityPathBase<Qna> {

    private static final long serialVersionUID = 328484046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQna qna = new QQna("qna");

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final NumberPath<Integer> qnaId = createNumber("qnaId", Integer.class);

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath title = createString("title");

    public final QUsers usersTb;

    public QQna(String variable) {
        this(Qna.class, forVariable(variable), INITS);
    }

    public QQna(Path<? extends Qna> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQna(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQna(PathMetadata metadata, PathInits inits) {
        this(Qna.class, metadata, inits);
    }

    public QQna(Class<? extends Qna> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usersTb = inits.isInitialized("usersTb") ? new QUsers(forProperty("usersTb")) : null;
    }

}

