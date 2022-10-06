package com.ssafy.indive.view.home

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.MainActivity
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentHomeBinding
import com.ssafy.indive.model.dto.Banner
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.USER
import com.ssafy.indive.utils.USER_EMAIL
import com.ssafy.indive.view.genre.genrelist.GenreListFragmentDirections
import com.ssafy.indive.view.player.PlayerFragmentDirections
import com.ssafy.indive.view.qrscan.QrScanActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun init() {
        binding.apply {
            homeVM = homeViewModel
        }

        initBanner()
        initRecentMusic()
        initPopularMusic()
        initClickListener()
        privateKeyCheck()
    }

    private fun initBanner() {
        val bannerList = mutableListOf(
            Banner(0, R.drawable.banner1),
            Banner(0, R.drawable.banner2),
            Banner(0, R.drawable.banner3)
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
            val intent = Intent(requireActivity(), QrScanActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecentMusic() {

        homeViewModel.getMusics(null, null, null, "latest", null, null, null)
        binding.rvRecentMusic.adapter = RecentMusicAdapter(playListener)

    }

    private fun initPopularMusic() {

        homeViewModel.getMusics(null, null, null, "popular", null, 0, 4)

        val moreListener: (MusicDetailResponse) -> (Unit) = {
            MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
                override fun clickDetail() {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToSongDetailFragment2(it.musicSeq)

                    findNavController().navigate(action)
                }

                override fun clickStudio() {
                    val mySeq = sharedPreferences.getLong(USER, 0L)
                    val memberSeq = it.artist.memberSeq

                    Log.d("HomeFragment_", "mySeq : $mySeq")
                    Log.d("HomeFragment_", "memberSeq : $memberSeq")

                    if (mySeq == memberSeq) {

                        findNavController().navigate(R.id.action_homeFragment_to_myStudioFragment)
                    } else {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToUserStudioFragment(memberSeq)

                        findNavController().navigate(action)
                    }
                }

                override fun clickReport() {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("http://pf.kakao.com/_lxeAjxj")
                    startActivity(i)
                }

            }).show(requireActivity().supportFragmentManager, "MoreDialog")
        }

        binding.rvMusicChart.adapter = MusicChartAdapter(playListener, moreListener)

    }

    private val playListener: (MusicDetailResponse) -> (Unit) = {
        mainViewModel.insert(it.musicSeq)
        mainViewModel.successGetEvent = it.musicSeq
    }

    fun scanQRSuccess() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDonateFragment(MainActivity.successQRScanMsg.toLong())
        MainActivity.successQRScanMsg = ""
        findNavController().navigate(action)
    }

    private fun privateKeyCheck(){
        val userEmail = sharedPreferences.getString(USER_EMAIL, "")!!
        val encryptedPrivateKey = sharedPreferences.getString(userEmail, "")!!
        // 개인키가 없을 때
        if(encryptedPrivateKey.isEmpty()){
            SetPrivateKeyDialogFragment().apply {
                show(this@HomeFragment.requireActivity().supportFragmentManager, "SetPrivateKeyDialogFragment")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (MainActivity.successQRScanMsg != "") {
            scanQRSuccess()
        }

    }
}