package com.ssafy.indive.domain.member.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberQueryRepository;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberDonationInfoResponseDto;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberDonationRankResponseDto;
import com.ssafy.indive.global.blockchain.InDive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.indive.global.constant.BlockchainConst.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberBlockchainService {

    private final MemberQueryRepository memberQueryRepository;
    public List<ServiceMemberDonationRankResponseDto> getDonationRanking(String address) {
        Web3j web3j = Web3j.build(new HttpService(BLOCKCHAIN_URL));
        Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);
        DefaultGasProvider gasProvider = new DefaultGasProvider();

        FastRawTransactionManager manager = new FastRawTransactionManager(
                web3j,
                credentials,
                new PollingTransactionReceiptProcessor(web3j, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY)
        );

        InDive inDive = InDive.load(INDIVE_ADDRESS, web3j, manager, gasProvider);

        String result = null;

        try {
            result = inDive.getDonatorList(address).sendAsync().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        List<ServiceMemberDonationInfoResponseDto> infoList = gson.fromJson(result, new TypeToken<List<ServiceMemberDonationInfoResponseDto>>() {
        }.getType());

        for (int i = 0 ; i < infoList.size() ; i++){
            if(infoList.get(i).getAddress().toLowerCase().equals(ADMIN_ADDRESS.toLowerCase())){
                infoList.remove(i);
                break;
            }
        }

        List<String> walletLists = infoList.stream().map(o -> o.getAddress()).collect(Collectors.toList());

        List<Member> byWallet = memberQueryRepository.findByWallet(walletLists);

        List<ServiceMemberDonationRankResponseDto> rankList = new ArrayList<>();

        for (int i = 0 ; i < walletLists.size() ; i++){
            for (int j = 0 ; j < byWallet.size() ; j++){
                if(walletLists.get(i).toLowerCase().equals(byWallet.get(j).getWallet().toLowerCase())) {
                    ServiceMemberDonationRankResponseDto dto = ServiceMemberDonationRankResponseDto.builder()
                            .memberSeq(byWallet.get(j).getSeq())
                            .imageUuid(byWallet.get(j).getImageUuid())
                            .nickname(byWallet.get(j).getNickname())
                            .address(byWallet.get(j).getWallet())
                            .totalValue(infoList.get(i).getTotalValue()).build();

                    rankList.add(dto);
                    break;
                }
            }
        }

        Collections.sort(rankList, new Comparator<ServiceMemberDonationRankResponseDto>(){
            @Override
            public int compare(ServiceMemberDonationRankResponseDto o1, ServiceMemberDonationRankResponseDto o2) {
                return o2.getTotalValue() - o1.getTotalValue();
            }
        });

        return rankList;
    }
}
