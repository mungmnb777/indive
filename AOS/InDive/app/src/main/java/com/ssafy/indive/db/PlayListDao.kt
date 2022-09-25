package com.ssafy.indive.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.indive.model.entity.PlayListEntity

@Dao
interface PlayListDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayList(playList : PlayListEntity)

    @Query("SELECT * FROM InDIve order by seq desc")
    fun getAllPlayList() : List<PlayListEntity>

}