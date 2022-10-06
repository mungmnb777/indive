package com.ssafy.indive.domain.nft.exception;

public class NftNotFoundException extends RuntimeException{

    public NftNotFoundException() {
        super();
    }

    public NftNotFoundException(String message) {
        super(message);
    }
}
