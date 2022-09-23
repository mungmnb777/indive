package com.ssafy.indive.model.dto

data class Music(
    val musicSeq: Long,
    val artistSeq: Long,
    val artistName: String,
    val artistImgSeq: Long,
    val coverImgSeq: Long,
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
)