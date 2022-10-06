package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://35.78.241.114:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "b6f60c49e72d48d55319547dbdd8a576c5483120837f127416f7c588c338497a";
    public static final String ADMIN_ADDRESS = "0x1be93e344660f4d187ec960029f90223b409a713";
    public static final String ADMIN_PASSWORD = "ssafy102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0x350ea279a34EE46438C32A2b944De98C02900303";
    public static final String INDIVETOKEN_ADDRESS = "0x480A110BFe0CA4C384aAAfd2FFCE39541a0224E9";
    public static final String INDIVE_ADDRESS = "0x7E0DADceB1637274B495D65D16e75820F842cb55";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}