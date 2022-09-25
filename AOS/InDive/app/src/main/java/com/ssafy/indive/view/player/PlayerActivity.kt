package com.ssafy.indive.view.player

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.ssafy.indive.MainActivity
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseActivity
import com.ssafy.indive.databinding.ActivityPlayerBinding
import com.ssafy.indive.model.dto.PlayListMusic
import com.ssafy.indive.model.dto.formatDuration
import com.ssafy.indive.model.dto.setSongPosition
import com.ssafy.indive.service.MusicService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PlayerActivity : BaseActivity<ActivityPlayerBinding>(R.layout.activity_player),
    ServiceConnection {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        lateinit var musicList: MutableList<PlayListMusic>
        var songPosition: Int = 0
        var musicService: MusicService? = null
        var isPlaying = false
        lateinit var playerBinding: ActivityPlayerBinding
    }

    private lateinit var updateSeekRunnable: Runnable
    private var isWatchingPlayList = false
    private lateinit var playListAdapter: PlayListAdapter
    override fun init() {
        context = applicationContext

        songPosition = intent.getIntExtra("index", 0)
        when (intent.getStringExtra("class")) {
            "NowPlaying" -> {
                initViews()
                if (isPlaying) {
                    binding.ivPlay.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_pause_24)
                } else {
                    binding.ivPlay.background =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_play_arrow_24)
                }

                binding.tvStartTime.text =
                    formatDuration(musicService!!.exoPlayer!!.currentPosition)
                binding.tvEndTime.text =
                    formatDuration(musicService!!.exoPlayer!!.duration)
                binding.playerSeekbar.progress = musicService!!.exoPlayer!!.currentPosition.toInt()
                binding.playerSeekbar.max = musicService!!.exoPlayer!!.duration.toInt()
            }

            "HomeFragment" -> {
                val intent = Intent(this, MusicService::class.java)
                bindService(intent, this, BIND_AUTO_CREATE)
                startService(intent)
                musicList = mutableListOf()
                musicList.addAll(MainActivity.playList)
                initViews()
            }
        }

        initClickListener()


        playerBinding = binding

        binding.playerSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                musicService!!.exoPlayer!!.seekTo((p0!!.progress * 1000).toLong())
            }

        })


    }

    private fun initClickListener() {

        binding.btnPlayerList.setOnClickListener {

            if (!isWatchingPlayList) {
                binding.groupPlayList.visibility = View.VISIBLE
                binding.groupSeekbar.visibility = View.GONE
                binding.groupPlayer.visibility = View.GONE
                isWatchingPlayList = true
            } else {
                binding.groupPlayList.visibility = View.GONE
                binding.groupSeekbar.visibility = View.VISIBLE
                binding.groupPlayer.visibility = View.VISIBLE
                isWatchingPlayList = false
            }

        }
        binding.ivPlay.setOnClickListener {
            if (isPlaying) pauseMusic()
            else playMusic()

        }

        binding.ivPrev.setOnClickListener {
            musicList[songPosition].isPlaying = false
            prevNextSong(false)
        }

        binding.ivNext.setOnClickListener {
            musicList[songPosition].isPlaying = false
            prevNextSong(true)
        }

        binding.ivShuffle.setOnClickListener {
            musicList[songPosition].isPlaying = false
            musicList.shuffle()
            initViews()
            createExoPlayer()
        }
    }

    private fun initViews() {
        Glide.with(this).load(musicList[songPosition].coverUrl).centerCrop()
            .into(binding.ivCoverImg)
        Glide.with(this).load(musicList[songPosition].coverUrl).centerCrop()
            .into(binding.ivBackground)
        binding.tvSongTitle.text = musicList[songPosition].track
        binding.tvSongArtist.text = musicList[songPosition].artist

        playListAdapter = PlayListAdapter { position ->
            musicList[songPosition].isPlaying = false
            songPosition = position

            initViews()
            createExoPlayer()
            playListAdapter.submitList(musicList)

        }
        playListAdapter.submitList(musicList)

        binding.rvPlayList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPlayList.adapter = playListAdapter
    }


    fun createExoPlayer() {

        CoroutineScope(Dispatchers.Main).launch {
            if (musicService!!.exoPlayer == null) musicService!!.exoPlayer =
                ExoPlayer.Builder(this@PlayerActivity).build()

            musicService!!.exoPlayer!!.addListener(object : Player.Listener {

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    updateSeekBar()
                }
            })

            musicService!!.exoPlayer!!.setMediaItem(MediaItem.fromUri(musicList[songPosition].streamUrl))
            musicService!!.exoPlayer!!.prepare()
            musicService!!.exoPlayer!!.play()
            musicList[songPosition].isPlaying = true
            isPlaying = true
            binding.ivPlay.background = ContextCompat.getDrawable(this@PlayerActivity,R.drawable.ic_baseline_pause_24)
            musicService!!.showNotification(R.drawable.ic_baseline_pause_24)

        }
    }

    fun updateSeekBar() {
        val playT = String.format(
            "%02d:%02d",
            TimeUnit.MINUTES.convert(
                musicService!!.exoPlayer!!.currentPosition,
                TimeUnit.MILLISECONDS
            ),
            (musicService!!.exoPlayer!!.currentPosition / 1000) % 60
        )

        if (musicService!!.exoPlayer!!.duration > 0) {
            val tot = String.format(
                "%02d:%02d",
                TimeUnit.MINUTES.convert(
                    musicService!!.exoPlayer!!.duration,
                    TimeUnit.MILLISECONDS
                ),
                (musicService!!.exoPlayer!!.duration / 1000) % 60
            )
            playerBinding.tvEndTime.text = tot
        }

        Log.d("PlayerActivity_", "updateSeekBar: ${musicService!!.exoPlayer!!.duration}")

        playerBinding.tvStartTime.text = playT


        playerBinding.playerSeekbar.progress = 0
        playerBinding.playerSeekbar.max = (musicService!!.exoPlayer!!.duration / 1000).toInt()
        seekBarSetup()

    }

    fun seekBarSetup() {

        updateSeekRunnable = Runnable {

            val playT = String.format(
                "%02d:%02d",
                TimeUnit.MINUTES.convert(
                    musicService!!.exoPlayer!!.currentPosition,
                    TimeUnit.MILLISECONDS
                ),
                (musicService!!.exoPlayer!!.currentPosition / 1000) % 60
            )


            playerBinding.tvStartTime.text = playT
            playerBinding.playerSeekbar.progress =
                (musicService!!.exoPlayer!!.currentPosition / 1000).toInt()

            Handler(Looper.getMainLooper()).postDelayed(updateSeekRunnable, 200)

        }
        Handler(Looper.getMainLooper()).postDelayed(updateSeekRunnable, 0)

    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createExoPlayer()

    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }

    fun playMusic() {
        binding.ivPlay.background = ContextCompat.getDrawable(this, R.drawable.ic_baseline_pause_24)
        musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        isPlaying = true
        musicService!!.exoPlayer!!.play()

    }

    fun pauseMusic() {
        binding.ivPlay.background =
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_play_arrow_24)
        musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        isPlaying = false
        musicService!!.exoPlayer!!.pause()

    }

    fun prevNextSong(increment: Boolean) {
        if (increment) {
            setSongPosition(true)
            initViews()
            createExoPlayer()
        } else {
            setSongPosition(false)
            initViews()
            createExoPlayer()
        }
    }


}