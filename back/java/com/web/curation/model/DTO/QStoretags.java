package com.web.curation.model.DTO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoretags is a Querydsl query type for Storetags
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStoretags extends EntityPathBase<Storetags> {

    private static final long serialVersionUID = 93671764L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoretags storetags = new QStoretags("storetags");

    public final QHashtag hashtag;

    public final NumberPath<Long> num = createNumber("num", Long.class);

    public final QReview review;

    public QStoretags(String variable) {
        this(Storetags.class, forVariable(variable), INITS);
    }

    public QStoretags(Path<? extends Storetags> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoretags(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoretags(PathMetadata metadata, PathInits inits) {
        this(Storetags.class, metadata, inits);
    }

    public QStoretags(Class<? extends Storetags> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

