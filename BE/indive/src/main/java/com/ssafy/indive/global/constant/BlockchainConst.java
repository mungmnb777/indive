package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://35.78.241.114:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "5f68e6367b07716a301253a6c44cd03fd3b6f885be87db9e96a6239c2fcac6ec";
    public static final String ADMIN_ADDRESS = "0xa674e936a8f5481d5f8085b481e6c422336b48d6";
    public static final String ADMIN_PASSWORD = "ssafy102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0xa999BA5c2777F6b17d6e6e3A1eec6dAAA4123eF0";
    public static final String INDIVETOKEN_ADDRESS = "0x3f64514FFe8C8933A2F6cf716DBD83e0049122a7";
    public static final String INDIVE_ADDRESS = "0xb413128f4Bd239cd844A926c3CCC65B2Ce38Ab6b";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}