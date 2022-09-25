package com.ssafy.indive.model.dto

import android.util.Log
import com.ssafy.indive.view.player.PlayerActivity
import java.util.concurrent.TimeUnit

data class PlayListMusic(

    val id : String,
    val track: String,
    val streamUrl: String,
    val artist: String,
    val coverUrl: String,
    var isPlaying : Boolean = false
)

fun formatDuration(duration : Long) : String{
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes* TimeUnit.SECONDS.convert(1,
        TimeUnit.MINUTES)
    return String.format("%02d:%02d",minutes,seconds)
}

fun setSongPosition(increment: Boolean){

    Log.d("setSongPosition", "increment: $increment ")
    Log.d("setSongPosition", "PlayerActivity.songPosition: ${PlayerActivity.songPosition} ")
    Log.d("setSongPosition", "PlayerActivity.musicList.size: ${PlayerActivity.musicList.size} ")
    if(increment){
        if(PlayerActivity.musicList.size -1 == PlayerActivity.songPosition){
            PlayerActivity.songPosition = 0
        }else{
            ++PlayerActivity.songPosition
        }
    }else{
        if(PlayerActivity.songPosition == 0){
            PlayerActivity.songPosition = PlayerActivity.musicList.size-1
        }else{
            --PlayerActivity.songPosition
        }
    }
}