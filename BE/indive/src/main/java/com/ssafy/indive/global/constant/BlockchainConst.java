package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://35.78.241.114:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "fcecb322ad7e18d14570b4fdadc11210f8c5118525b47993d3254c219d237645";
    public static final String ADMIN_ADDRESS = "0xbbfbdab165949bdd64f39ef179bb47859bc074a2";
    public static final String ADMIN_PASSWORD = "ssafy102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0x3CCc77fca952f9A8Eff86226d30927a43F87939a";
    public static final String INDIVETOKEN_ADDRESS = "0xE41EDa1eC37ed84AC607ee5f6BC999918c39e028";
    public static final String INDIVE_ADDRESS = "0x6565Bed7cdA813Ff9B4cA5ebe490CE605d5eC415";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}