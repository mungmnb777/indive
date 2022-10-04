package com.ssafy.indive.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1505525069L;

    public static final QMember member = new QMember("member1");

    public final com.ssafy.indive.global.common.entity.QBaseEntity _super = new com.ssafy.indive.global.common.entity.QBaseEntity(this);

    public final StringPath backgroundOrigin = createString("backgroundOrigin");

    public final StringPath backgroundUuid = createString("backgroundUuid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createMember = _super.createMember;

    public final StringPath email = createString("email");

    public final StringPath imageOrigin = createString("imageOrigin");

    public final StringPath imageUuid = createString("imageUuid");

    public final StringPath nickname = createString("nickname");

    public final StringPath notice = createString("notice");

    public final StringPath password = createString("password");

    public final StringPath profileMessage = createString("profileMessage");

    public final EnumPath<com.ssafy.indive.domain.member.entity.enumeration.Role> role = createEnum("role", com.ssafy.indive.domain.member.entity.enumeration.Role.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    //inherited
    public final StringPath updateMember = _super.updateMember;

    public final StringPath wallet = createString("wallet");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

