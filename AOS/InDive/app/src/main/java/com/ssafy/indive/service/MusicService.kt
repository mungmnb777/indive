package com.ssafy.indive.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.ssafy.indive.NotificationReceiver
import com.ssafy.indive.R
import com.ssafy.indive.di.ApplicationClass
import com.ssafy.indive.view.player.PlayerActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MusicService : Service() {
    private var myBinder = MyBinder()
    var exoPlayer: ExoPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var updateSeekRunnable : Runnable

    override fun onBind(p0: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext, "My Music")
        return myBinder
    }


    inner class MyBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }

    fun showNotification(playPauseBtn: Int) {

        val playIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(
            ApplicationClass.PLAY
        )
        val playPendingIntent =
            PendingIntent.getBroadcast(baseContext, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)

        val prevIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.PREV)
        val prevPendingIntent =
            PendingIntent.getBroadcast(baseContext, 0, prevIntent, PendingIntent.FLAG_IMMUTABLE)

        val nextIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent =
            PendingIntent.getBroadcast(baseContext, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivity.musicList[PlayerActivity.songPosition].track)
            .setContentText(PlayerActivity.musicList[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.img_ive_cover))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_baseline_skip_previous_24, "Prev", prevPendingIntent)
            .addAction(playPauseBtn, "Play", playPendingIntent)
            .addAction(R.drawable.ic_baseline_skip_next_24, "Next", nextPendingIntent)
            .build()


        startForeground(13, notification)
    }

    fun createExoPlayer() {

        CoroutineScope(Dispatchers.Main).launch {
            if (PlayerActivity.musicService!!.exoPlayer == null) PlayerActivity.musicService!!.exoPlayer =
                ExoPlayer.Builder(PlayerActivity.context!!).build()

            PlayerActivity.musicService!!.exoPlayer!!.addListener(object : Player.Listener{

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    updateSeekBar()
                }
            })

            PlayerActivity.musicService!!.exoPlayer!!.setMediaItem(MediaItem.fromUri(PlayerActivity.musicList[PlayerActivity.songPosition].streamUrl))
            PlayerActivity.musicService!!.exoPlayer!!.prepare()
            PlayerActivity.playerBinding.ivPlay.background = ContextCompat.getDrawable(PlayerActivity.context!!,R.drawable.ic_baseline_pause_24)
            PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)


        }
    }

    fun updateSeekBar() {
        val playT = String.format(
            "%02d:%02d",
            TimeUnit.MINUTES.convert(exoPlayer!!.currentPosition, TimeUnit.MILLISECONDS),
            (exoPlayer!!.currentPosition / 1000) % 60
        )

        if(PlayerActivity.musicService!!.exoPlayer!!.duration > 0){
            val tot = String.format(
                "%02d:%02d",
                TimeUnit.MINUTES.convert(PlayerActivity.musicService!!.exoPlayer!!.duration, TimeUnit.MILLISECONDS),
                (PlayerActivity.musicService!!.exoPlayer!!.duration / 1000) % 60
            )
            PlayerActivity.playerBinding.tvEndTime.text = tot
        }

        PlayerActivity.playerBinding.tvStartTime.text = playT

        PlayerActivity.playerBinding.playerSeekbar.progress = 0
        PlayerActivity.playerBinding.playerSeekbar.max = (exoPlayer!!.duration / 1000).toInt()
        seekBarSetup()

    }

    fun seekBarSetup() {

        updateSeekRunnable = Runnable {

            val playT = String.format(
                "%02d:%02d",
                TimeUnit.MINUTES.convert(exoPlayer!!.currentPosition, TimeUnit.MILLISECONDS),
                (exoPlayer!!.currentPosition / 1000) % 60
            )
            PlayerActivity.playerBinding.tvStartTime.text = playT
            PlayerActivity.playerBinding.playerSeekbar.progress = (exoPlayer!!.currentPosition / 1000).toInt()

            Handler(Looper.getMainLooper()).postDelayed(updateSeekRunnable, 200)

        }
        Handler(Looper.getMainLooper()).postDelayed(updateSeekRunnable, 0)

    }
}