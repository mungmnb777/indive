package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://j7d1021.p.ssafy.io:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "55306712e22fff116da7ef2002478d2bfe71d91f88d50cc8e66ca9d9bc5f55bb";
    public static final String ADMIN_ADDRESS = "0x4DFb299B49764e891dD68a9c6E237FAE725C78f3";
    public static final String ADMIN_PASSWORD = "102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0x1f0D803d693fC0B153f2f26456dD09c10BD3c09e";
    public static final String INDIVETOKEN_ADDRESS = "0x75205DE0205c4fcbF5d0c373Bd357E1cB44D985B";
    public static final String INDIVE_ADDRESS = "0x3f5E1354dC7caD73A24C68238AaBa7909b27B61F";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}