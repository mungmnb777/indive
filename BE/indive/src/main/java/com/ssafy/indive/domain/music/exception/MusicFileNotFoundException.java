package com.ssafy.indive.domain.music.exception;

public class MusicFileNotFoundException extends RuntimeException {

    public MusicFileNotFoundException() {
        super();
    }

    public MusicFileNotFoundException(String message) {
        super(message);
    }
}
