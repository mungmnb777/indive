package com.ssafy.indive.datasource

import android.util.Log
import com.ssafy.indive.api.NFTApi
import com.ssafy.indive.model.dto.NFT
import com.ssafy.indive.model.dto.NFTAmount
import com.ssafy.indive.model.response.NFTStockResponse
import com.ssafy.indive.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NFTDataSource @Inject constructor(
    private val nftApi: NFTApi
){

    fun addNFT(image: MultipartBody.Part, dto: Map<String, RequestBody>): Flow<Boolean> = flow{
        emit(nftApi.addNFT(image, dto))
    }

    fun putRewardNFT(nftAmount: NFTAmount) : Flow<Boolean> = flow{
        emit(nftApi.putRewardNFT(nftAmount))
    }

    fun checkIsGetNFT(artistSeq: Long) : Flow<NFTStockResponse> = flow {
        emit(nftApi.checkIsGetNFT(artistSeq))
    }

}