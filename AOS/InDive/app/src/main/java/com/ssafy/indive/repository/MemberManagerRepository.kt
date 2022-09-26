package com.ssafy.indive.repository

import com.ssafy.indive.datasource.MemberManagerDataSource
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.model.dto.MemberLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

@Singleton
class MemberManagerRepository @Inject constructor(
    private val memberManagerDataSource: MemberManagerDataSource){

    fun login(memberLogin: MemberLogin): Flow<Result<Response<String>>> = flow {
        emit(Result.Loading)
        memberManagerDataSource.login(memberLogin).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun join(memberJoin: MemberJoin): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        memberManagerDataSource.join(memberJoin).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}