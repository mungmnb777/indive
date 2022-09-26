package com.ssafy.indive.model.dto

data class MemberJoin(
    val email: String,
    val password : String,
    val nickname: String,
    val wallet: String,
    val profileMessage: String,
)