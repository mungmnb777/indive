package com.ssafy.indive.domain.music.entity.key;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MusicLikeCompositeKey implements Serializable {

    private Long liker;

    private Long music;
}
