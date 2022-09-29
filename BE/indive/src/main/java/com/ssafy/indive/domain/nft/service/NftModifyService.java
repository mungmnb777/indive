package com.ssafy.indive.domain.nft.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.nft.entity.Nft;
import com.ssafy.indive.domain.nft.exception.NftNotFoundException;
import com.ssafy.indive.domain.nft.exception.NotSatisfiedAmountException;
import com.ssafy.indive.domain.nft.repository.NftQueryRepository;
import com.ssafy.indive.domain.nft.service.dto.ServiceNftModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NftModifyService {

    private final MemberRepository memberRepository;

    private final NftQueryRepository nftQueryRepository;

    public boolean issueNft(ServiceNftModifyRequestDto dto) {
        Member artist = memberRepository.findById(dto.getArtistSeq()).orElseThrow(IllegalArgumentException::new);

        Nft nft = nftQueryRepository.findByArtist(artist).orElseThrow(() -> new NftNotFoundException("해당 아티스트의 NFT가 없습니다."));

        if (!nft.isOverLowerDonationAmount(dto.getAmount())) throw new NotSatisfiedAmountException("후원 최소 금액을 넘지 못했습니다.");

        nft.minusStock();

        return true;
    }
}
