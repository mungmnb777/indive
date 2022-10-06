package com.ssafy.indive.repository

import com.ssafy.indive.datasource.NFTDataSource
import com.ssafy.indive.model.dto.NFT
import com.ssafy.indive.model.dto.NFTAmount
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.NFTStockResponse
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NFTRepository @Inject constructor(
    private val nftDataSource: NFTDataSource
){

    fun addNFT(image: MultipartBody.Part, dto: Map<String, RequestBody>): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        nftDataSource.addNFT(image, dto).collect {
            emit(Result.Success(it))
        }

    }.catch { e ->
        emit(Result.Error(e))
    }

    fun putRewardNFT(nftAmount: NFTAmount) : Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        nftDataSource.putRewardNFT(nftAmount).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun checkIsGetNFT(artistSeq: Long): Flow<Result<NFTStockResponse>> = flow {
        emit(Result.Loading)
        nftDataSource.checkIsGetNFT(artistSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

}