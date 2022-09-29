package com.ssafy.indive.view.player

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentNowPlayingBinding

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

        binding.ivNowPlayingPlay.setOnClickListener {
            if (PlayerFragment.isPlaying) pauseMusic() else playMusic()
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

    }

    override fun onResume() {
        super.onResume()
        if (PlayerFragment.musicService != null) {
            binding.root.visibility = View.VISIBLE
            binding.tvNowPlayingTitle.isSelected = true

            Glide.with(this).load(PlayerFragment.musicList[PlayerFragment.songPosition].coverUrl)
                .centerCrop()
                .into(binding.ivNowPlayingCover)
            binding.tvNowPlayingTitle.text =
                PlayerFragment.musicList[PlayerFragment.songPosition].track

            if (PlayerFragment.isPlaying) binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            else binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)

        }
    }

    private fun playMusic() {
        PlayerFragment.musicService!!.exoPlayer!!.play()
        binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        PlayerFragment.isPlaying = true
    }

    private fun pauseMusic() {
        PlayerFragment.musicService!!.exoPlayer!!.pause()
        binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        PlayerFragment.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        PlayerFragment.isPlaying = false
    }

}