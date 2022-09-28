package com.ssafy.indive.view.home

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.MainActivity
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentHomeBinding
import com.ssafy.indive.model.dto.Banner
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.view.qrscan.QrScanActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        initClickListener()
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


    private fun initClickListener() {
        binding.tvMoreMusicChart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_musicChartFragment)
        }

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.ivQr.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_QRFragment)
//            val intent = Intent(requireActivity(),PlayerActivity::class.java)
//            startActivity(intent)
            val intent = Intent(requireActivity(), QrScanActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecentMusic() {

        homeViewModel.getMusics(null, null, "latest", null,null,null)
        binding.rvRecentMusic.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentMusic.adapter = RecentMusicAdapter(playListener)


    }

    private fun initPopularMusic() {
        homeViewModel.getMusics(null, null, "popular", null,0,4)

        binding.rvMusicChart.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val moreListener: (MusicDetailResponse) -> (Unit) = {
            MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
                override fun clickDetail() {
//                    val intent = Intent(context, SongDetailActivity::class.java)
//                    startActivity(intent)
                }

                override fun clickStudio() {
                    findNavController().navigate(R.id.action_homeFragment_to_userStudioFragment)
                }

                override fun clickReport() {

                }

            }).show(requireActivity().supportFragmentManager, "MoreDialog")
        }

        binding.rvMusicChart.adapter = MusicChartAdapter(playListener, moreListener)


    }


    private val playListener: (MusicDetailResponse) -> (Unit) = {
        mainViewModel.insert(it.musicSeq)
        mainViewModel.successGetEvent=it.musicSeq
    }
    fun scanQRSuccess(){
        val action = HomeFragmentDirections.actionHomeFragmentToDonateFragment(MainActivity.successQRScanMsg)
        MainActivity.successQRScanMsg = ""
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if(MainActivity.successQRScanMsg != ""){
            scanQRSuccess()
        }

    }
}