package com.ssafy.indive.model.dto

import android.util.Log
import com.ssafy.indive.view.player.PlayerActivity
import com.ssafy.indive.view.player.PlayerFragment
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

    Log.d("setSongPosition2", "increment: $increment ")
    Log.d("setSongPosition2", "PlayerActivity.songPosition: ${PlayerFragment.songPosition} ")
    Log.d("setSongPosition2", "PlayerActivity.musicList.size: ${PlayerFragment.musicList.size} ")
    if(increment){
        if(PlayerFragment.musicList.size -1 == PlayerFragment.songPosition){
            PlayerFragment.songPosition = 0
        }else{
            ++PlayerFragment.songPosition
        }
    }else{
        if(PlayerFragment.songPosition == 0){
            PlayerFragment.songPosition = PlayerFragment.musicList.size-1
        }else{
            --PlayerFragment.songPosition
        }
    }
}