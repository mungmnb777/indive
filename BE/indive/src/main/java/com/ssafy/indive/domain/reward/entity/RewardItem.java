package com.ssafy.indive.domain.reward.entity;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.reward.service.dto.ServiceItemModifyRequestDto;
import com.ssafy.indive.global.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ri_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ri_artist_seq")
    private Member artist;

    @Column(name = "ri_title")
    private String title;

    @Column(name = "ri_content")
    private String content;

    @Column(name = "ri_point")
    private int point;

    @Column(name = "ri_stock")
    private int stock;

    @Column(name = "ri_image_origin")
    private String imageOrigin;

    @Column(name = "ri_image_uuid")
    private String imageUuid;

    @Builder
    public RewardItem(Long seq, Member artist, String title, String content, int point, int stock, String imageOrigin, String imageUuid) {
        this.seq = seq;
        this.artist = artist;
        this.title = title;
        this.content = content;
        this.point = point;
        this.stock = stock;
        this.imageOrigin = imageOrigin;
        this.imageUuid = imageUuid;
    }

    public void update(ServiceItemModifyRequestDto dto) {
        content = dto.getContent();
        point = dto.getPoint();
        content = dto.getContent();
    }
}
