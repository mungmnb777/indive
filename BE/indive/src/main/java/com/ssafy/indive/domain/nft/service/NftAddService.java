package com.ssafy.indive.domain.nft.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.nft.entity.Nft;
import com.ssafy.indive.domain.nft.repository.NftRepository;
import com.ssafy.indive.domain.nft.service.dto.ServiceNftAddRequestDto;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
@RequiredArgsConstructor
public class NftAddService {

    private final NftRepository nftRepository;

    public boolean addImageToIpfs(ServiceNftAddRequestDto dto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        IPFS ipfs = new IPFS("/ip4/3.34.252.202/tcp/5001");

        InputStream inputStream = new ByteArrayInputStream(dto.getImage().getBytes());

        NamedStreamable.InputStreamWrapper isw = new NamedStreamable.InputStreamWrapper(inputStream);

        MerkleNode response = ipfs.add(isw).get(0);

        Nft nft = Nft.builder()
                .artist(loginMember)
                .cid(response.hash.toBase58())
                .lowerDonationAmount(dto.getLowerDonationAmount())
                .stock(dto.getStock())
                .build();

        nftRepository.save(nft);

        return true;
    }
}
