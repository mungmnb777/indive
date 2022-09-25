package com.ssafy.indive.domain.reward.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.reward.entity.RewardItem;
import com.ssafy.indive.domain.reward.entity.RewardPoint;
import com.ssafy.indive.domain.reward.repository.RewardItemRepository;
import com.ssafy.indive.domain.reward.repository.RewardPointRepository;
import com.ssafy.indive.domain.reward.service.dto.ServiceItemAddRequestDto;
import com.ssafy.indive.domain.reward.service.dto.ServicePointAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardAddService {

    private final MemberRepository memberRepository;

    private final RewardPointRepository rewardPointRepository;

    private final RewardItemRepository rewardItemRepository;

    // TODO : fan 멤버를 로그인한 유저로 변경해야 함
    public boolean addPoint(ServicePointAddRequestDto dto) {
        Member artist = memberRepository.findById(dto.getArtistSeq()).orElseThrow(IllegalArgumentException::new);

        RewardPoint point = RewardPoint.builder()
                .fan(null)
                .artist(artist)
                .point((int) Math.floor(dto.getCoin()))
                .build();

        rewardPointRepository.save(point);

        return true;
    }

    // TODO : artist를 로그인한 유저로 변경해야 함
    public boolean addItem(ServiceItemAddRequestDto dto) {

        RewardItem item = RewardItem.builder()
                .artist(null)
                .title(dto.getTitle())
                .content(dto.getContent())
                .point(dto.getPoint())
                .stock(dto.getStock())
                .build();

        rewardItemRepository.save(item);

        return true;
    }

}
