package com.ssafy.indive.view.player

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentNowPlayingBinding

class NowPlayingFragment : BaseFragment<FragmentNowPlayingBinding>(R.layout.fragment_now_playing) {

    companion object{

        lateinit var nowPlayingBinding: FragmentNowPlayingBinding


    }
    override fun init() {
        nowPlayingBinding = binding
        binding.root.visibility = View.INVISIBLE

        binding.ivNowPlayingPlay.setOnClickListener {
            if(PlayerActivity.isPlaying) pauseMusic() else playMusic()
        }

        binding.root.setOnClickListener {
            val intent = Intent(requireContext(),PlayerActivity::class.java)
            intent.putExtra("index",PlayerActivity.songPosition)
            intent.putExtra("class","NowPlaying")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(PlayerActivity.musicService!=null){
            binding.root.visibility = View.VISIBLE
            binding.tvNowPlayingTitle.isSelected = true

            Glide.with(this).load(PlayerActivity.musicList[PlayerActivity.songPosition].coverUrl).centerCrop()
                .into(binding.ivNowPlayingCover)
            binding.tvNowPlayingTitle.text = PlayerActivity.musicList[PlayerActivity.songPosition].track

            if(PlayerActivity.isPlaying) binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            else binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)

        }
    }

    private fun playMusic(){
        PlayerActivity.musicService!!.exoPlayer!!.play()
        binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_pause_24)
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        PlayerActivity.isPlaying = true
    }
    private fun pauseMusic(){
        PlayerActivity.musicService!!.exoPlayer!!.pause()
        binding.ivNowPlayingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        PlayerActivity.isPlaying = false
    }

}