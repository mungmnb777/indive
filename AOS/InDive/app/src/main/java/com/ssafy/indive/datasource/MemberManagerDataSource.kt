package com.ssafy.indive.datasource

import com.ssafy.indive.api.MemberManagerApi
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.model.dto.MemberLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberManagerDataSource @Inject constructor(
    private val memberManagerApi: MemberManagerApi
) {
    fun login(memberLogin: MemberLogin): Flow<Response<String>> = flow{
        emit(memberManagerApi.login(memberLogin))
    }
    fun join(memberJoin: MemberJoin): Flow<Response<Boolean>> = flow {
        emit(memberManagerApi.join(memberJoin))
    }
    fun emailcheck(email: String): Flow<Response<Boolean>> = flow {
        emit(memberManagerApi.emailCheck(email))
    }

}