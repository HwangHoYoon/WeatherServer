package com.jagiya.weather.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWeather is a Querydsl query type for Weather
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeather extends EntityPathBase<Weather> {

    private static final long serialVersionUID = 904100291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWeather weather = new QWeather("weather");

    public final StringPath baseDate = createString("baseDate");

    public final StringPath baseTime = createString("baseTime");

    public final StringPath fcstDate = createString("fcstDate");

    public final StringPath fcstTime = createString("fcstTime");

    public final com.jagiya.juso.entity.QJusoGroup jusoGroup;

    public final StringPath latX = createString("latX");

    public final StringPath lonY = createString("lonY");

    public final DateTimePath<java.util.Date> modifyDate = createDateTime("modifyDate", java.util.Date.class);

    public final StringPath pcp = createString("pcp");

    public final StringPath pop = createString("pop");

    public final StringPath pty = createString("pty");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public final StringPath sky = createString("sky");

    public final StringPath tmn = createString("tmn");

    public final StringPath tmp = createString("tmp");

    public final StringPath tmx = createString("tmx");

    public final NumberPath<Long> weatherId = createNumber("weatherId", Long.class);

    public QWeather(String variable) {
        this(Weather.class, forVariable(variable), INITS);
    }

    public QWeather(Path<? extends Weather> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWeather(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWeather(PathMetadata metadata, PathInits inits) {
        this(Weather.class, metadata, inits);
    }

    public QWeather(Class<? extends Weather> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jusoGroup = inits.isInitialized("jusoGroup") ? new com.jagiya.juso.entity.QJusoGroup(forProperty("jusoGroup")) : null;
    }

}

