package com.ssafy.indive.domain.music.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMusicLike is a Querydsl query type for MusicLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMusicLike extends EntityPathBase<MusicLike> {

    private static final long serialVersionUID = 549842710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMusicLike musicLike = new QMusicLike("musicLike");

    public final com.ssafy.indive.global.common.entity.QBaseEntity _super = new com.ssafy.indive.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createMember = _super.createMember;

    public final com.ssafy.indive.domain.member.entity.QMember liker;

    public final QMusic music;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateMember = _super.updateMember;

    public QMusicLike(String variable) {
        this(MusicLike.class, forVariable(variable), INITS);
    }

    public QMusicLike(Path<? extends MusicLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMusicLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMusicLike(PathMetadata metadata, PathInits inits) {
        this(MusicLike.class, metadata, inits);
    }

    public QMusicLike(Class<? extends MusicLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.liker = inits.isInitialized("liker") ? new com.ssafy.indive.domain.member.entity.QMember(forProperty("liker")) : null;
        this.music = inits.isInitialized("music") ? new QMusic(forProperty("music"), inits.get("music")) : null;
    }

}

