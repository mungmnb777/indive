package com.ssafy.indive.domain.nft.exception;

public class NotSatisfiedAmountException extends RuntimeException{
    public NotSatisfiedAmountException() {
        super();
    }

    public NotSatisfiedAmountException(String message) {
        super(message);
    }
}
