package com.ssafy.indive.domain.nft.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.nft.entity.Nft;
import com.ssafy.indive.domain.nft.exception.NftNotFoundException;
import com.ssafy.indive.domain.nft.exception.NotSatisfiedAmountException;
import com.ssafy.indive.domain.nft.repository.NftQueryRepository;
import com.ssafy.indive.domain.nft.service.dto.ServiceNftModifyRequestDto;
import com.ssafy.indive.global.blockchain.InDiveNFT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import static com.ssafy.indive.global.constant.BlockchainConst.*;
import static com.ssafy.indive.global.constant.BlockchainConst.ADMIN_PASSWORD;

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
        else {
            Web3j web3j = Web3j.build(new HttpService(BLOCKCHAIN_URL));
            Admin admin = Admin.build(new HttpService(BLOCKCHAIN_URL));
            Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);
            DefaultGasProvider gasProvider = new DefaultGasProvider();

            FastRawTransactionManager manager = new FastRawTransactionManager(
                    web3j,
                    credentials,
                    CHAIN_ID,
                    new PollingTransactionReceiptProcessor(web3j, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
            );

            InDiveNFT inDiveNFT = InDiveNFT.load(INDIVENFT_ADDRESS, web3j, manager, gasProvider);

            String transactionHash = null;

            try {
                admin.personalUnlockAccount(ADMIN_ADDRESS, ADMIN_PASSWORD).sendAsync().get();
                transactionHash = inDiveNFT.safeMint(dto.getWallet(), nft.getCid()).sendAsync().get().getTransactionHash();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        nft.minusStock();

        return true;
    }
}
