package com.ssafy.indive.domain.music.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMusicGetCondition {

    private String title;

    private String artist;

    private String sort;

    private String genre;
}
