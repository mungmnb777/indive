package com.ssafy.indive.model.response

import com.ssafy.indive.model.dto.Member

data class ReplyResponse(
    val member : Member,
    val replySeq : Long,
    val content : String
)
