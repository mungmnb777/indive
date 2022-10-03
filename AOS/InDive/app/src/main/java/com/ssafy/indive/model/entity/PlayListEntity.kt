package com.ssafy.indive.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InDIve")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    var seq: Int = 0,
    val memberSeq : Long,
    val memberAddress : String,
    val musicSeq: Long,
    val title: String,
    val streamUrl: String,
    val artist: String,
    val coverUrl: String
)
