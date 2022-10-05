package com.ssafy.indive.domain.nft.service.facade;

import com.ssafy.indive.domain.nft.service.NftModifyService;
import com.ssafy.indive.domain.nft.service.dto.ServiceNftModifyRequestDto;
import com.ssafy.indive.global.common.redis.repository.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NftModifyServiceRedisFacade {

    private final RedisLockRepository redisLockRepository;

    private final NftModifyService nftModifyService;

    public boolean issueNft(ServiceNftModifyRequestDto dto) {
        try {
            while (!redisLockRepository.lock(dto.getArtistSeq())) {
                Thread.sleep(100);
            }
            nftModifyService.issueNft(dto);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            redisLockRepository.unlock(dto.getArtistSeq());
        }

        return true;
    }
}
