package com.ssafy.indive.domain.nft.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNft is a Querydsl query type for Nft
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNft extends EntityPathBase<Nft> {

    private static final long serialVersionUID = 1216336895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNft nft = new QNft("nft");

    public final com.ssafy.indive.global.common.entity.QBaseEntity _super = new com.ssafy.indive.global.common.entity.QBaseEntity(this);

    public final com.ssafy.indive.domain.member.entity.QMember artist;

    public final StringPath cid = createString("cid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createMember = _super.createMember;

    public final NumberPath<Integer> lowerDonationAmount = createNumber("lowerDonationAmount", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateMember = _super.updateMember;

    public QNft(String variable) {
        this(Nft.class, forVariable(variable), INITS);
    }

    public QNft(Path<? extends Nft> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNft(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNft(PathMetadata metadata, PathInits inits) {
        this(Nft.class, metadata, inits);
    }

    public QNft(Class<? extends Nft> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new com.ssafy.indive.domain.member.entity.QMember(forProperty("artist")) : null;
    }

}

