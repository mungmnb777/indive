package com.ssafy.indive.model.response

data class MemberDetailResponse(
    val memberSeq : Long,
    val email : String,
    val nickname: String,
    val role: String,
    val wallet: String,
    val profileMessage: String,
    val notice: String
)
