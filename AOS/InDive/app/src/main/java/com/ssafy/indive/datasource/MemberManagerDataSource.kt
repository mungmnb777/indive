package com.ssafy.indive.datasource

import com.ssafy.indive.api.MemberManagerApi
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.model.dto.MemberLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberManagerDataSource @Inject constructor(
    private val memberManagerApi: MemberManagerApi
) {
    fun login(memberLogin: MemberLogin): Flow<Response<String>> = flow {
        emit(memberManagerApi.login(memberLogin))
    }

    fun join(memberJoin: MemberJoin): Flow<Response<Boolean>> = flow {
        emit(memberManagerApi.join(memberJoin))
    }

    fun modifyMember(
        memberSeq: Long,
        nickname: String,
        profileFile: MultipartBody.Part?,
        backgroundFile: MultipartBody.Part?,
        profileMessage: String?
    ): Flow<Boolean> = flow {
        emit(
            memberManagerApi.modifyMember(
                memberSeq,
                nickname,
                profileFile,
                backgroundFile,
                profileMessage
            )
        )
    }

}