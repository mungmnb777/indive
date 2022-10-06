package com.ssafy.indive.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.indive.model.entity.PlayListEntity

@Database(entities = [PlayListEntity::class],version = 1,exportSchema = false)
abstract class PlayListDatabase : RoomDatabase(){
    abstract fun playListDao() : PlayListDao
}