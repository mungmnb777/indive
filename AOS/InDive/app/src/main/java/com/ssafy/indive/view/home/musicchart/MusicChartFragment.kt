package com.ssafy.indive.view.home.musicchart

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMusicchartBinding
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter

class MusicChartFragment : BaseFragment<FragmentMusicchartBinding>(R.layout.fragment_musicchart) {
    private val homeViewModel : HomeViewModel by viewModels()
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
        binding.rvMusicChartDetail.adapter = MusicChartAdapter()
    }
}