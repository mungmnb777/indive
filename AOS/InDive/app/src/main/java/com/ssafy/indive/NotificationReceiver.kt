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
import com.ssafy.indive.view.player.PlayerFragment

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        when (p1?.action) {
            ApplicationClass.PLAY -> {
                Log.d("NotificationReceiver2", "onReceive:${PlayerFragment.isPlaying} ")
                if (PlayerFragment.isPlaying) pauseMusic() else playMusic()
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
        PlayerFragment.isPlaying = true
        PlayerFragment.musicList[PlayerFragment.songPosition].isPlaying = true
        PlayerFragment.musicService!!.exoPlayer!!.play()
        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        PlayerFragment.playerBinding.ivPlay.background = ContextCompat.getDrawable(PlayerFragment.mContext!!,R.drawable.ic_baseline_pause_24)
        NowPlayingFragment.nowPlayingBinding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
        Glide.with(NowPlayingFragment.mContext!!).load(PlayerFragment.musicList[PlayerFragment.songPosition].coverUrl).centerCrop()
            .into(NowPlayingFragment.nowPlayingBinding.ivNowPlayingCover)
        NowPlayingFragment.nowPlayingBinding.tvNowPlayingTitle.text = PlayerFragment.musicList[PlayerFragment.songPosition].track


    }

    private fun pauseMusic() {
        PlayerFragment.isPlaying = false
        PlayerFragment.musicService!!.exoPlayer!!.pause()
        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        PlayerFragment.playerBinding.ivPlay.background = ContextCompat.getDrawable(PlayerFragment.mContext!!,R.drawable.ic_baseline_play_arrow_24)
        NowPlayingFragment.nowPlayingBinding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }

    private fun prevNextMusic(increment: Boolean, context: Context) {
        PlayerFragment.musicList[PlayerFragment.songPosition].isPlaying = false
        setSongPosition(increment = increment)
        PlayerFragment.musicService!!.createExoPlayer()
        Log.d("setSongPosition2", "prevNextMusic: ${PlayerFragment.songPosition}")

        Glide.with(context).load(PlayerFragment.musicList[PlayerFragment.songPosition].coverUrl)
            .into(PlayerFragment.playerBinding.ivCoverImg)
        Glide.with(context).load(PlayerFragment.musicList[PlayerFragment.songPosition].coverUrl)
            .into(PlayerFragment.playerBinding.ivBackground)
        PlayerFragment.playerBinding.tvSongTitle.text =
            PlayerFragment.musicList[PlayerFragment.songPosition].track
        PlayerFragment.playerBinding.tvSongArtist.text =
            PlayerFragment.musicList[PlayerFragment.songPosition].artist

        playMusic()
    }

}