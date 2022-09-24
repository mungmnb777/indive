package com.ssafy.indive.domain.box.entity.key;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoxMusicCompositeKey implements Serializable {

    private Long box;

    private Long music;
}
