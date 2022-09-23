package com.ssafy.indive.repository

import com.ssafy.indive.api.MusicManagerApi
import com.ssafy.indive.datasource.MusicManagerDataSource
import com.ssafy.indive.model.dto.Music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicManagerRepository @Inject constructor(
    private val musicManagerDataSource: MusicManagerDataSource
){

    fun getMusics(title: String?, artistName: String?, sort: String?, genre: String?) : Flow<Result<List<Music>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.getMusics(title, artistName, sort, genre).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

}