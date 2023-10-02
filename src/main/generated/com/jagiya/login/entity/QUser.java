package com.jagiya.login.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -914959279L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> admin = createNumber("admin", Integer.class);

    public final DateTimePath<java.util.Date> agreeDate = createDateTime("agreeDate", java.util.Date.class);

    public final NumberPath<Integer> agreeFalg = createNumber("agreeFalg", Integer.class);

    public final DateTimePath<java.util.Date> deleteDate = createDateTime("deleteDate", java.util.Date.class);

    public final NumberPath<Integer> deleteFlag = createNumber("deleteFlag", Integer.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath snsId = createString("snsId");

    public final NumberPath<Integer> snsType = createNumber("snsType", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

