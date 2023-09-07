package com.jagiya.main.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeather is a Querydsl query type for Weather
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeather extends EntityPathBase<Weather> {

    private static final long serialVersionUID = 560546270L;

    public static final QWeather weather = new QWeather("weather");

    public final StringPath baseDate = createString("baseDate");

    public final StringPath baseTime = createString("baseTime");

    public final StringPath fcstDate = createString("fcstDate");

    public final StringPath fcstTime = createString("fcstTime");

    public final NumberPath<Integer> latX = createNumber("latX", Integer.class);

    public final NumberPath<Integer> lonY = createNumber("lonY", Integer.class);

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
        super(Weather.class, forVariable(variable));
    }

    public QWeather(Path<? extends Weather> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeather(PathMetadata metadata) {
        super(Weather.class, metadata);
    }

}

