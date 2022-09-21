package com.ssafy.indive.view.home.musicchart

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMusicchartBinding
import com.ssafy.indive.utils.TAG
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter

class MusicChartFragment : BaseFragment<FragmentMusicchartBinding>(R.layout.fragment_musicchart) {
    private val homeViewModel : HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun init() {

        binding.apply {
            homeVM = homeViewModel
        }

        initPopularMusic()

    }

    private fun initPopularMusic() {
        homeViewModel.initPopularSongList()
        binding.rvMusicChartDetail.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        binding.rvMusicChartDetail.adapter = MusicChartAdapter{
            Log.d(TAG, "initPopularMusic: $it")
            mainViewModel.play()
        }
    }
}