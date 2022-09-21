package com.ssafy.indive.domain.reward.entity;

import com.ssafy.indive.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardPoint {

    @Id @GeneratedValue
    @Column(name = "rp_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rp_artist_seq")
    private Member artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rp_fan_seq")
    private Member fan;

    @Column(name = "rp_point")
    private int point;

    @Builder
    public RewardPoint(Long seq, Member artist, Member fan, int point) {
        this.seq = seq;
        this.artist = artist;
        this.fan = fan;
        this.point = point;
    }
}
