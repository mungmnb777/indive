package com.ssafy.indive.model.dto

import okhttp3.MultipartBody

data class NFT(
    val img: MultipartBody.Part,
    val cost: Int,
    val quantity: Int
)
