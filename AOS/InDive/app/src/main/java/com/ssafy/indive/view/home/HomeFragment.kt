package com.ssafy.indive.view.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentHomeBinding
import com.ssafy.indive.model.dto.Banner
import com.ssafy.indive.utils.TAG
import com.ssafy.indive.MainViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init() {

        binding.apply {
            homeVM = homeViewModel
        }

        initBanner()
        initRecentMusic()
        initPopularMusic()

        clickListener()
    }

    private fun initBanner() {
        val bannerList = mutableListOf(
            Banner(0, R.drawable.ic_launcher_foreground),
            Banner(0, R.drawable.ic_launcher_background),
            Banner(0, R.drawable.ic_launcher_foreground)
        )
        binding.vpBanner.adapter = BannerAdapter(bannerList)
        binding.circleIndicator.setViewPager(binding.vpBanner)
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
        binding.rvMusicChart.adapter = MusicChartAdapter{
            Log.d(TAG, "initPopularMusic: $it")
            mainViewModel.play()

        }


    }
}