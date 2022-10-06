package com.ssafy.indive.view.home

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.ssafy.indive.utils.*
import com.ssafy.indive.view.genre.genrelist.GenreListFragmentDirections
import com.ssafy.indive.view.player.PlayerFragmentDirections
import com.ssafy.indive.view.qrscan.QrScanActivity
import com.ssafy.indive.view.userstudio.donate.FingerPrintDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var popularAdapter : MusicChartAdapter
    private lateinit var recentAdapter : RecentMusicAdapter

    override fun onStart() {
        super.onStart()

    }

    override fun init() {
        binding.apply {
            homeVM = homeViewModel
        }

        initBanner()
        initRecentMusic()
        initPopularMusic()
        initClickListener()
        privateKeyCheck()
        showFingerPrintDialog()
        initViewModelCallback()
    }

    private fun initViewModelCallback(){
        lifecycleScope.launch {
            homeViewModel.popularMusicList.collectLatest {
                if(it is Result.Success) {
                    popularAdapter.submitList(listOf())

                    popularAdapter.submitList(it.data)
                }
            }
        }

        lifecycleScope.launch {
            homeViewModel.latestMusicList.collectLatest {
                if(it is Result.Success) {
                    recentAdapter.submitList(listOf())

                    recentAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun showFingerPrintDialog() {
        if (sharedPreferences.getInt(FINGERPRINT_USE, DISABLE) != 1) {
//            FingerPrintDialog().show(parentFragmentManager, "FingerPrintDialog")
            showToast("지문 인증 설정해주세요")
        }
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
        recentAdapter = RecentMusicAdapter(playListener)
        binding.rvRecentMusic.adapter = recentAdapter

    }

    private fun initPopularMusic() {

        homeViewModel.getMusics(null, null, null, "popular", null, null, null)

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
        popularAdapter = MusicChartAdapter(playListener, moreListener)
        binding.rvMusicChart.adapter = popularAdapter

    }

    private val playListener: (MusicDetailResponse) -> (Unit) = {
        mainViewModel.insert(it.musicSeq)
        mainViewModel.successGetEvent = it.musicSeq
    }

    fun scanQRSuccess() {
        Log.d(TAG, "scanQRSuccess: ")
        val artistSeq = MainActivity.successQRScanMsg
        MainActivity.successQRScanMsg = ""
        val action =
            HomeFragmentDirections.actionHomeFragmentToDonateFragment(artistSeq.toLong())
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