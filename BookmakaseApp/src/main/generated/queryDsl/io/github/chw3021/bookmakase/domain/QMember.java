package io.github.chw3021.bookmakase.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1768947810L;

    public static final QMember member = new QMember("member1");

    public final StringPath account = createString("account");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<io.github.chw3021.bookmakase.member.Authority, io.github.chw3021.bookmakase.member.QAuthority> roles = this.<io.github.chw3021.bookmakase.member.Authority, io.github.chw3021.bookmakase.member.QAuthority>createList("roles", io.github.chw3021.bookmakase.member.Authority.class, io.github.chw3021.bookmakase.member.QAuthority.class, PathInits.DIRECT2);

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

