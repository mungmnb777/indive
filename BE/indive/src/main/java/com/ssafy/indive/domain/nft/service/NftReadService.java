package com.ssafy.indive.domain.nft.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.nft.entity.Nft;
import com.ssafy.indive.domain.nft.exception.NftNotFoundException;
import com.ssafy.indive.domain.nft.repository.NftQueryRepository;
import com.ssafy.indive.domain.nft.service.dto.ServiceCheckAmountGetRequestDto;
import com.ssafy.indive.domain.nft.service.dto.ServiceCheckStockGetRequestDto;
import com.ssafy.indive.domain.nft.service.dto.ServiceNftAmountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NftReadService {

    private final MemberRepository memberRepository;

    private final NftQueryRepository nftQueryRepository;

    public ServiceNftAmountResponseDto checkStock(ServiceCheckStockGetRequestDto dto) {
        Member artist = memberRepository.findById(dto.getArtistSeq()).orElseThrow(IllegalArgumentException::new);

        Nft nft = nftQueryRepository.findByArtist(artist).orElseThrow(() -> new NftNotFoundException("false"));

        return new ServiceNftAmountResponseDto(nft.getLowerDonationAmount());
    }

    public boolean checkAmount(ServiceCheckAmountGetRequestDto dto) {
        Member artist = memberRepository.findById(dto.getArtistSeq()).orElseThrow(IllegalArgumentException::new);

        Nft nft = nftQueryRepository.findByArtist(artist).orElseThrow(() -> new NftNotFoundException("해당 아티스트의 NFT가 없습니다."));

        return dto.getAmount() >= nft.getLowerDonationAmount();
    }
}
