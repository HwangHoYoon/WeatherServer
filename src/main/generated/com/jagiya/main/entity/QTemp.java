package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTemp is a Querydsl query type for Temp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemp extends EntityPathBase<Temp> {

    private static final long serialVersionUID = 1593152042L;

    public static final QTemp temp = new QTemp("temp");

    public final StringPath fld01 = createString("fld01");

    public final StringPath fld02 = createString("fld02");

    public final StringPath fld03 = createString("fld03");

    public final StringPath fld04 = createString("fld04");

    public final StringPath fld05 = createString("fld05");

    public final StringPath fld06 = createString("fld06");

    public final StringPath fld07 = createString("fld07");

    public final StringPath fld08 = createString("fld08");

    public final StringPath fld09 = createString("fld09");

    public final StringPath fld10 = createString("fld10");

    public final StringPath fld11 = createString("fld11");

    public final StringPath fld12 = createString("fld12");

    public final StringPath fld13 = createString("fld13");

    public final StringPath fld14 = createString("fld14");

    public final StringPath fld15 = createString("fld15");

    public final StringPath fld16 = createString("fld16");

    public final StringPath fld17 = createString("fld17");

    public final StringPath fld18 = createString("fld18");

    public final StringPath fld19 = createString("fld19");

    public final StringPath fld20 = createString("fld20");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QTemp(String variable) {
        super(Temp.class, forVariable(variable));
    }

    public QTemp(Path<? extends Temp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTemp(PathMetadata metadata) {
        super(Temp.class, metadata);
    }

}

