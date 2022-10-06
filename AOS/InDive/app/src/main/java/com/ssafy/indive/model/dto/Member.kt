package com.ssafy.indive.model.dto

data class Member(
    val memberSeq: Long,
    val email: String,
    val nickname: String,
    val role: String,
    val wallet: String,
    val profileMessage: String,
    val notice: String

)
