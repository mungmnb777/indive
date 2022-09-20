package com.ssafy.indive.view.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentHomeBinding
import com.ssafy.indive.model.dto.Song

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    override fun init() {

        binding.apply {
            homeVM = homeViewModel
        }
        initRecentMusic()
        initPopularMusic()

        clickListener()
    }

    private fun clickListener() {
        binding.tvMoreMusicChart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_musicChartFragment)
        }

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.ivQr.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_QRFragment)
        }

    }

    private fun initRecentMusic() {

        homeViewModel.initRecentSongList()
        binding.rvRecentMusic.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentMusic.adapter = RecentMusicAdapter()


    }

    private fun initPopularMusic() {

        homeViewModel.initPopularSongList()
        binding.rvMusicChart.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMusicChart.adapter = MusicChartAdapter()


    }
}