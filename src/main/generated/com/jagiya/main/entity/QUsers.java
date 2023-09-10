package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = 2144406130L;

    public static final QUsers users = new QUsers("users");

    public final DateTimePath<java.util.Date> agreesDate = createDateTime("agreesDate", java.util.Date.class);

    public final NumberPath<Integer> agreesFalg = createNumber("agreesFalg", Integer.class);

    public final DateTimePath<java.util.Date> birthday = createDateTime("birthday", java.util.Date.class);

    public final StringPath ci = createString("ci");

    public final DateTimePath<java.util.Date> ciDate = createDateTime("ciDate", java.util.Date.class);

    public final NumberPath<Integer> deleteFlag = createNumber("deleteFlag", Integer.class);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isAdmin = createNumber("isAdmin", Integer.class);

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final StringPath nickname = createString("nickname");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> snsConnectDate = createDateTime("snsConnectDate", java.util.Date.class);

    public final StringPath snsName = createString("snsName");

    public final StringPath snsProfile = createString("snsProfile");

    public final NumberPath<Integer> snsType = createNumber("snsType", Integer.class);

    public final StringPath username = createString("username");

    public final NumberPath<Long> usersId = createNumber("usersId", Long.class);

    public final StringPath uuid = createString("uuid");

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

