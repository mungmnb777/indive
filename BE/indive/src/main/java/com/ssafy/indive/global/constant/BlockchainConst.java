package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://35.78.241.114:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "f5a6424b8fe4a1bcae3cd81bb60ac4c0304d759fda8c6afd527ab4ba3c389014";
    public static final String ADMIN_ADDRESS = "0x30f382372923eb431E809e78D9dE6A1A384FBc43";
    public static final String ADMIN_PASSWORD = "ssafy102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0xEdA17C3DdAa7BCa3e1BDaB8Be51299f140E77a11";
    public static final String INDIVETOKEN_ADDRESS = "0xa2C15Ec81b75B3bcC9467df23B8aE9396D874786";
    public static final String INDIVE_ADDRESS = "0x0ed3998e696B585CA86b396F008f8d908A81eEa5";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}