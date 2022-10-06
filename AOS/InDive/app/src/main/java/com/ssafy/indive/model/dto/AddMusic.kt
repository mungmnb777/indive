package com.ssafy.indive.model.dto

import okhttp3.MultipartBody

data class AddMusic(
    val title: String,
    val lyricist: String,
    val composer: String,
    val genre: String,
    val description: String,
    val lyrics: String,
    val releaseDateTime: String,
    val reservationDateTime: String,
)
