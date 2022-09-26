package com.ssafy.indive.model.response

import com.ssafy.indive.model.dto.Member

data class MusicDetailResponse(
    val musicSeq: Long,
    val member: Member,
    val title: String,
    val lyricist: String,
    val composer: String,
    val genre: String,
    val description: String,
    val lyrics: String,
    val releaseDateTime: String,
    val reservationDateTime: String,
    val createDate: String,
    val updateDate: String,
    val likeCount: Int

)
