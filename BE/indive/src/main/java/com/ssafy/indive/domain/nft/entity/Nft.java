package com.ssafy.indive.domain.nft.entity;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.global.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nft extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "nft_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_artist_seq")
    private Member artist;

    @Column(name = "nft_cid")
    private String cid;

    @Column(name = "nft_lower_donation_amount")
    private int lowerDonationAmount;

    @Column(name = "nft_stock")
    private int stock;

    @Builder
    public Nft(Long seq, Member artist, String cid, int lowerDonationAmount, int stock) {
        this.seq = seq;
        this.artist = artist;
        this.cid = cid;
        this.lowerDonationAmount = lowerDonationAmount;
        this.stock = stock;
    }

    public void minusStock() {
        stock--;
    }

    public boolean isOverLowerDonationAmount(int amount) {
        return amount >= lowerDonationAmount;
    }
}
