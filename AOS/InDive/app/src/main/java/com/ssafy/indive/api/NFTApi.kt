package com.ssafy.indive.api

import com.ssafy.indive.model.dto.NFT
import com.ssafy.indive.model.dto.NFTAmount
import com.ssafy.indive.model.response.NFTStockResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface NFTApi {

    @POST("nft")
    @Multipart
    @JvmSuppressWildcards
    suspend fun addNFT(
        @Part image: MultipartBody.Part,
        @PartMap dto: Map<String, RequestBody>
        ) : Boolean

    @PUT("nft")
    suspend fun putRewardNFT(@Body nftAmount: NFTAmount) : Boolean

    @GET("nft/check-stock")
    suspend fun checkIsGetNFT(@Query("artistSeq") artistSeq: Long) : NFTStockResponse

}