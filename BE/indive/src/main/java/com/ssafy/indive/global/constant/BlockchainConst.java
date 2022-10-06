package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://j7d1021.p.ssafy.io:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "999b371e4f52d8c362752f745ecda82984bde6230f8e1d4e52c7bb565500d3d8";
    public static final String ADMIN_ADDRESS = "0x947D88949256942a6ef904E0C8b324AA6948c167";
    public static final String ADMIN_PASSWORD = "ssafy102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0x18814C29781607302c15426628F2c0E7c0Ca7EDB";
    public static final String INDIVETOKEN_ADDRESS = "0x3C520aAb4B51a6e9bf0EF34c9c6a9f84c09f4530";
    public static final String INDIVE_ADDRESS = "0x411b15a40dcd465Eaf05F240c293f9c63e393d2d";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}