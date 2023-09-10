package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaAnswer is a Querydsl query type for QnaAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaAnswer extends EntityPathBase<QnaAnswer> {

    private static final long serialVersionUID = 1120149036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaAnswer qnaAnswer = new QQnaAnswer("qnaAnswer");

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final NumberPath<Integer> qnaId = createNumber("qnaId", Integer.class);

    public final QQna qnaTb;

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath reviewers = createString("reviewers");

    public final StringPath title = createString("title");

    public QQnaAnswer(String variable) {
        this(QnaAnswer.class, forVariable(variable), INITS);
    }

    public QQnaAnswer(Path<? extends QnaAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaAnswer(PathMetadata metadata, PathInits inits) {
        this(QnaAnswer.class, metadata, inits);
    }

    public QQnaAnswer(Class<? extends QnaAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaTb = inits.isInitialized("qnaTb") ? new QQna(forProperty("qnaTb"), inits.get("qnaTb")) : null;
    }

}

