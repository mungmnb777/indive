package com.ssafy.indive.model.dto

data class MusicDetail(
    val artist: Artist,
    val composer: String,
    val createDate: String,
    val description: String,
    val genre: String,
    val likeCount: String,
    val lyricist: String,
    val lyrics: String,
    val musicSeq: String,
    val releaseDateTime: String,
    val reservationDateTime: String,
    val title: String,
    val updateDate: String
)