package com.ssafy.indive.model.dto

data class Song(
    val musicSeq: String,
    val title: String,
    val lyricist: String,
    val composer: String,
    val genre: String,
    val description: String,
    val lyrics: String,
    val releaseDateTime: String,
    val reservationDateTime: String,
    val createDate: String,
    val updateDate: String
)