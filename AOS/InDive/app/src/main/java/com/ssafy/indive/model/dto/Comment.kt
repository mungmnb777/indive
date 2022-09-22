package com.ssafy.indive.model.dto

data class Comment(
    val commentSeq: Long,
    val userSeq: Long,
    val profileImg: Int,
    val nickname: String,
    val regDate: String,
    val comment: String
)
