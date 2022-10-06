package com.ssafy.indive.model.response

data class DonationRankResponse (
    val memberSeq : Long,
    val nickname: String,
    val address : String,
    val totalValue : Int
)