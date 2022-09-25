package com.ssafy.indive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ssafy.indive.di.ApplicationClass
import com.ssafy.indive.model.dto.setSongPosition
import com.ssafy.indive.view.player.NowPlayingFragment
import com.ssafy.indive.view.player.PlayerActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        when (p1?.action) {
            ApplicationClass.PLAY -> {
                if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
            }
            ApplicationClass.PREV -> {
                prevNextMusic(false, context = p0!!)
            }
            ApplicationClass.NEXT -> {
                prevNextMusic(true, context = p0!!)
            }

        }
    }

    private fun playMusic() {
        PlayerActivity.isPlaying = true
        PlayerActivity.musicList[PlayerActivity.songPosition].isPlaying = true

        PlayerActivity.musicService!!.exoPlayer!!.play()
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        PlayerActivity.playerBinding.ivPlay.background = ContextCompat.getDrawable(PlayerActivity.context!!,R.drawable.ic_baseline_pause_24)
        NowPlayingFragment.nowPlayingBinding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
    }

    private fun pauseMusic() {
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService!!.exoPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        PlayerActivity.playerBinding.ivPlay.background = ContextCompat.getDrawable(PlayerActivity.context!!,R.drawable.ic_baseline_play_arrow_24)
        NowPlayingFragment.nowPlayingBinding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }

    private fun prevNextMusic(increment: Boolean, context: Context) {
        PlayerActivity.musicList[PlayerActivity.songPosition].isPlaying = false
        setSongPosition(increment = increment)
        PlayerActivity.musicService!!.createExoPlayer()
        Log.d("setSongPosition", "prevNextMusic: ${PlayerActivity.songPosition}")

        Glide.with(context).load(PlayerActivity.musicList[PlayerActivity.songPosition].coverUrl)
            .into(PlayerActivity.playerBinding.ivCoverImg)
        Glide.with(context).load(PlayerActivity.musicList[PlayerActivity.songPosition].coverUrl)
            .into(PlayerActivity.playerBinding.ivBackground)
        PlayerActivity.playerBinding.tvSongTitle.text =
            PlayerActivity.musicList[PlayerActivity.songPosition].track
        PlayerActivity.playerBinding.tvSongArtist.text =
            PlayerActivity.musicList[PlayerActivity.songPosition].artist

        playMusic()
    }

}