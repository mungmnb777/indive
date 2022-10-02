package com.ssafy.indive.datasource

import com.ssafy.indive.api.MemberManagerApi
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.model.dto.MemberLogin
import com.ssafy.indive.model.dto.Notice
import com.ssafy.indive.model.response.MemberDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
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

    fun emailcheck(email: String): Flow<Response<Boolean>> = flow {
        emit(memberManagerApi.emailCheck(email))
    }

    fun memberDetail(memberSeq: Long): Flow<Response<MemberDetailResponse>> = flow {
        emit(memberManagerApi.memberDetail(memberSeq))
    }

    fun modifyMember(
        memberSeq: Long,
        nickname: String,
        profileFile: MultipartBody.Part?,
        backgroundFile: MultipartBody.Part?,
        profileMessage: String?
    ): Flow<Response<Boolean>> = flow {
        if (backgroundFile == null && profileFile == null) {
            emit(
                memberManagerApi.memberModify(
                    memberSeq,
                    nickname,
                    profileMessage
                )
            )
        } else if (backgroundFile == null) {
            emit(
                memberManagerApi.memberModify(
                    memberSeq,
                    nickname,
                    profileFile,
                    profileMessage
                )
            )
        } else {
            emit(
                memberManagerApi.memberModify(
                    memberSeq,
                    nickname,
                    profileFile,
                    backgroundFile,
                    profileMessage
                )
            )
        }

    }


    fun writeNotice(memberSeq: Long, notice: Notice): Flow<Boolean> = flow {
        emit(memberManagerApi.writeNotice(memberSeq, notice))
    }

    fun loginMemberDetail(): Flow<Response<MemberDetailResponse>> = flow {
        emit(memberManagerApi.loginMemberDetail())
    }

}