package com.ssafy.indive.repository

import com.ssafy.indive.datasource.local.InDiveLocalDataSource
import com.ssafy.indive.model.entity.PlayListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayListRepository  @Inject constructor(private val inDiveLocalDataSource: InDiveLocalDataSource) {
    fun insertPlayList(playListEntity: PlayListEntity) = inDiveLocalDataSource.insertPlayList(playListEntity)
    fun getAllPlayList() : List<PlayListEntity> = inDiveLocalDataSource.getAllPlayList()

}