package com.ssafy.indive.repository

import android.util.Log
import com.ssafy.indive.datasource.local.InDiveLocalDataSource
import com.ssafy.indive.model.entity.PlayListEntity
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayListRepository @Inject constructor(private val inDiveLocalDataSource: InDiveLocalDataSource) {
    fun insertPlayList(playListEntity: PlayListEntity) =
        inDiveLocalDataSource.insertPlayList(playListEntity)

    fun getAllPlayList(): Flow<Result<List<PlayListEntity>>> = flow {
        Log.d("PlayListRepository", "getAllPlayList: Loading ")
        emit(Result.Loading)
        inDiveLocalDataSource.getAllPlayList().collect {
            Log.d("PlayListRepository", "getAllPlayList: collect ")
            if (it.isEmpty()) {
                Log.d("PlayListRepository", "getAllPlayList: Empty ")
                emit(Result.Empty)
            } else {
                Log.d("PlayListRepository", "getAllPlayList: Success ")
                emit(Result.Success(it))
            }
        }
    }.catch { e -> emit(Result.Error(e)) }

}