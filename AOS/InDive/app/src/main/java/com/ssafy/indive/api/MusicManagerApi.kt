package com.ssafy.indive.api

import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.ReplyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface MusicManagerApi {

    @GET("music")
    suspend fun getMusics(
        @Query("title") title: String?, @Query("artist") artistName: String?,
        @Query("sort") sort: String?, @Query("genre") genre: String?
    ): List<Music>

    @Multipart
    @POST("music")
    @JvmSuppressWildcards
    suspend fun addMusic(
        @PartMap dto : Map<String,RequestBody>,
        @Part image : MultipartBody.Part?,
        @Part musicFile : MultipartBody.Part

    ): Boolean

    @GET("music/{musicSeq}")
    suspend fun getMusicDetails(@Path("musicSeq") musicSeq: Long): Response<MusicDetailResponse>

    @PUT("music/{musicSeq}")
    suspend fun modifyMusic(@Path("musicSeq") musicSeq: Long): Response<Boolean>

    @DELETE("music/{musicSeq}")
    suspend fun deleteMusic(@Path("musicSeq") musicSeq: Long): Response<Boolean>

    @POST("music/{musicSeq}/like")
    suspend fun likeMusic(@Path("musicSeq") musicSeq: Long): Response<Boolean>

    @GET("music/{musicSeq}/like")
    suspend fun isLike(@Path("musicSeq") musicSeq: Long): Response<Boolean>

    @DELETE("music/{musicSeq}/like")
    suspend fun deleteLike(@Path("musicSeq") musicSeq: Long): Response<Boolean>

    @GET("music/{musicSeq}/like/count")
    suspend fun getLikeCount(@Path("musicSeq") musicSeq: Long): Response<Int>

    @POST("music/{musicSeq}/reply")
    suspend fun addMusicReply(@Path("musicSeq") musicSeq: Long): Response<Boolean>

    @GET("music/{musicSeq}/reply")
    suspend fun getMusicReply(@Path("musicSeq") musicSeq: Long): Response<List<ReplyResponse>>

    @PUT("music/{musicSeq}/reply/{replySeq}")
    suspend fun modifyMusicReply(
        @Path("musicSeq") musicSeq: Long,
        @Path("replySeq") replySeq: Long
    ): Response<Boolean>

    @GET("music/{musicSeq}/reply")
    suspend fun deleteMusicReply(@Path("musicSeq") musicSeq: Long): Response<Boolean>


}