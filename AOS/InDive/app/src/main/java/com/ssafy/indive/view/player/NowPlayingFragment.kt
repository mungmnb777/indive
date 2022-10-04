package com.ssafy.indive.view.player

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentNowPlayingBinding
import com.ssafy.indive.model.dto.setSongPosition

class NowPlayingFragment : BaseFragment<FragmentNowPlayingBinding>(R.layout.fragment_now_playing) {

    companion object {

        lateinit var nowPlayingBinding: FragmentNowPlayingBinding

        @SuppressLint("StaticFieldLeak")
        var mContext: Context? = null

    }

    override fun init() {
        mContext = context
        nowPlayingBinding = binding
        binding.root.visibility = View.INVISIBLE

        initClickListener()

    }

    private fun initClickListener() {
        binding.ivNowPlayingPlay.setOnClickListener {
            if (PlayerFragment.isPlaying) pauseMusic() else playMusic()
        }

        binding.ivNowPlayingNext.setOnClickListener {
            prevNextMusic(true)
        }
        binding.ivNowPlayingPrev.setOnClickListener {
            prevNextMusic(false)
        }

        binding.root.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerFragment.songPosition)
            intent.putExtra("class", "NowPlaying")
            startActivity(intent)
        }

        binding.root.setOnLongClickListener {
            if (PlayerFragment.isPlaying) pauseMusic()
            binding.root.visibility = View.INVISIBLE
            true
        }

        binding.ivPlayList.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerFragment.songPosition)
            intent.putExtra("class", "NowPlaying")
            intent.putExtra("type", "homeBox")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (PlayerFragment.musicService != null) {

            if (PlayerFragment.musicList.isNotEmpty()) {
                binding.root.visibility = View.VISIBLE
                binding.tvNowPlayingTitle.isSelected = true

                binding.tvNowPlayingArtist.text =
                    PlayerFragment.musicList[PlayerFragment.songPosition].artist
                binding.tvNowPlayingTitle.text =
                    PlayerFragment.musicList[PlayerFragment.songPosition].track

                if (PlayerFragment.isPlaying) binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_48)
                else binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_48)
            }


        }
    }

    private fun playMusic() {
        PlayerFragment.isPlaying = true
        PlayerFragment.musicList[PlayerFragment.songPosition].isPlaying = true
        PlayerFragment.musicService!!.exoPlayer!!.play()
        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        PlayerFragment.playerBinding.ivPlay.background =
            ContextCompat.getDrawable(PlayerFragment.mContext!!, R.drawable.ic_baseline_pause_24)
        nowPlayingBinding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_48)
        nowPlayingBinding.tvNowPlayingTitle.text =
            PlayerFragment.musicList[PlayerFragment.songPosition].track
        nowPlayingBinding.tvNowPlayingArtist.text =
            PlayerFragment.musicList[PlayerFragment.songPosition].artist


    }

    private fun pauseMusic() {
        PlayerFragment.isPlaying = false
        PlayerFragment.musicService!!.exoPlayer!!.pause()
        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        PlayerFragment.playerBinding.ivPlay.background = ContextCompat.getDrawable(
            PlayerFragment.mContext!!,
            R.drawable.ic_baseline_play_arrow_24
        )
        nowPlayingBinding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_48)
    }
//    private fun playMusic() {
//        PlayerFragment.musicService!!.exoPlayer!!.play()
//        binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
//        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
//        PlayerFragment.isPlaying = true
//    }
//
//    private fun pauseMusic() {
//        PlayerFragment.musicService!!.exoPlayer!!.pause()
//        binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
//        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
//        PlayerFragment.isPlaying = false
//    }

    private fun prevNextMusic(increment: Boolean) {
        PlayerFragment.musicList[PlayerFragment.songPosition].isPlaying = false
        setSongPosition(increment = increment)
        PlayerFragment.musicService!!.createExoPlayer()
        Log.d("setSongPosition2", "prevNextMusic: ${PlayerFragment.songPosition}")

        PlayerFragment.playerBinding.tvSongTitle.text =
            PlayerFragment.musicList[PlayerFragment.songPosition].track
        PlayerFragment.playerBinding.tvSongArtist.text =
            PlayerFragment.musicList[PlayerFragment.songPosition].artist

        playMusic()
    }


}