package com.ssafy.indive.domain.member.exception;

public class NotMatchMemberException extends RuntimeException {
    public NotMatchMemberException() {
        super();
    }

    public NotMatchMemberException(String message) {
        super(message);
    }
}
