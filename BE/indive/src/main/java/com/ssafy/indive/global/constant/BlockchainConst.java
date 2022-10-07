package com.ssafy.indive.global.constant;

public class BlockchainConst {

    //블록체인 네트워크
    public static final String BLOCKCHAIN_URL = "http://j7d1021.p.ssafy.io:8545";
    public static final long CHAIN_ID = 102L;

    //어드민 정보
    public static final String ADMIN_PRIVATE_KEY = "d8c4538badc5305f045d951ebd5b1d248aa46810dcea7cdaa7f315e5d981b146";
    public static final String ADMIN_ADDRESS = "0x563fceaa6aec3fa6782787b615a574f20b3d62de";
    public static final String ADMIN_PASSWORD = "ssafy102";

    //컨트랙트 주소
    public static final String INDIVENFT_ADDRESS = "0xC6B48b25B5E057D61790fC97DE787E29Ae002749";
    public static final String INDIVETOKEN_ADDRESS = "0x02381F301aA9EB7a7AAFDE8fF70f7B6aeC33dd3f";
    public static final String INDIVE_ADDRESS = "0xec36EbB93B466EB3252a12937B24FdfEaCe90c60";

    public static final long TX_END_CHECK_DURATION = 3000L;
    public static final int TX_END_CHECK_RETRY = 3;
}