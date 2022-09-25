package com.ssafy.indive.datasource.local

import com.ssafy.indive.db.PlayListDao
import com.ssafy.indive.model.entity.PlayListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InDiveLocalDataSource @Inject constructor(private val playListDao: PlayListDao) {

    fun insertPlayList(playList: PlayListEntity) = playListDao.insertPlayList(playList)

    fun getAllPlayList(): List<PlayListEntity> = playListDao.getAllPlayList()

}