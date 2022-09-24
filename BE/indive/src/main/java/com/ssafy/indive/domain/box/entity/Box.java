package com.ssafy.indive.domain.box.entity;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.global.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Box extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="box_seq")
    private Long seq;

    @Column(name="box_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="box_member_seq")
    private Member member;

    @OneToMany
    List<BoxMusic> boxMusics = new ArrayList<>();

    @Builder
    public Box(Long seq, String name, Member member) {
        this.seq = seq;
        this.name = name;
        this.member = member;
    }


}
