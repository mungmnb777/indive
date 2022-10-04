package com.ssafy.indive.domain.music.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMusic is a Querydsl query type for Music
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMusic extends EntityPathBase<Music> {

    private static final long serialVersionUID = 450122463L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMusic music = new QMusic("music");

    public final com.ssafy.indive.global.common.entity.QBaseEntity _super = new com.ssafy.indive.global.common.entity.QBaseEntity(this);

    public final com.ssafy.indive.domain.member.entity.QMember author;

    public final StringPath composer = createString("composer");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createMember = _super.createMember;

    public final StringPath description = createString("description");

    public final StringPath genre = createString("genre");

    public final StringPath imageOrigin = createString("imageOrigin");

    public final StringPath imageUuid = createString("imageUuid");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath lyricist = createString("lyricist");

    public final StringPath lyrics = createString("lyrics");

    public final StringPath musicFileOrigin = createString("musicFileOrigin");

    public final StringPath musicFileUuid = createString("musicFileUuid");

    public final ListPath<MusicLike, QMusicLike> musicLikes = this.<MusicLike, QMusicLike>createList("musicLikes", MusicLike.class, QMusicLike.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> releaseDatetime = createDateTime("releaseDatetime", java.time.LocalDateTime.class);

    public final ListPath<Reply, QReply> replies = this.<Reply, QReply>createList("replies", Reply.class, QReply.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> reservationDatetime = createDateTime("reservationDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateMember = _super.updateMember;

    public QMusic(String variable) {
        this(Music.class, forVariable(variable), INITS);
    }

    public QMusic(Path<? extends Music> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMusic(PathMetadata metadata, PathInits inits) {
        this(Music.class, metadata, inits);
    }

    public QMusic(Class<? extends Music> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.ssafy.indive.domain.member.entity.QMember(forProperty("author")) : null;
    }

}

