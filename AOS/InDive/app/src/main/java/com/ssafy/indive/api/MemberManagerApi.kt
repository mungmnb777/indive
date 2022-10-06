package com.ssafy.indive.api

import com.ssafy.indive.model.response.DonationRankResponse
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.model.dto.MemberLogin
import com.ssafy.indive.model.dto.Notice
import com.ssafy.indive.model.response.MemberDetailResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MemberManagerApi {
    // 로그인
    @POST("members/login")
    suspend fun login(@Body memberLogin: MemberLogin): Response<String>

    // 회원가입
    @POST("members/join")
    suspend fun join(@Body memberJoin: MemberJoin): Response<Boolean>

    // 멤버 세부 정보
    @GET("members/{memberSeq}")
    suspend fun memberDetail(@Path("memberSeq") memberSeq: Long): Response<MemberDetailResponse>

    // 이메일 중복확인
    @GET("members/duplicated-email")
    suspend fun emailCheck(@Query("email") email: String): Response<Boolean>

    // 멤버 정보 수정
    @Multipart
    @PUT("members/{memberSeq}")
    suspend fun memberModify(
        @Path("memberSeq") memberSeq: Long,
        @Query("nickname") nickname: String,
        @Part image: MultipartBody.Part?,
        @Part background: MultipartBody.Part?,
        @Query("profileMessage") profileMessage: String?
    ): Response<Boolean>

    // 멤버 정보 수정
    @Multipart
    @PUT("members/{memberSeq}")
    suspend fun memberModify(
        @Path("memberSeq") memberSeq: Long,
        @Query("nickname") nickname: String,
        @Part image: MultipartBody.Part?,
        @Query("profileMessage") profileMessage: String?
    ): Response<Boolean>

    // 파일 둘 다 없을 때
    @PUT("members/{memberSeq}")
    suspend fun memberModify(
        @Path("memberSeq") memberSeq: Long,
        @Query("nickname") nickname: String,
        @Query("profileMessage") profileMessage: String?
    ): Response<Boolean>


    // 공지사항 작성
    @POST("members/{memberSeq}/notice")
    suspend fun writeNotice(@Path("memberSeq") memberSeq: Long, @Body notice: Notice): Boolean

    // 로그인 멤버 정보
    @GET("members/my-account")
    suspend fun loginMemberDetail(): Response<MemberDetailResponse>

    // 해당 사용자의 후원 랭킹
    @GET("members/donation-rank/{address}")
    suspend fun donationRankingByAddress(@Path("address") address: String) : Response<List<DonationRankResponse>>
}