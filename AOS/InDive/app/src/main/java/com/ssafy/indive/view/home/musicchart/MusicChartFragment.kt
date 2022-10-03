package com.ssafy.indive.view.home.musicchart

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMusicchartBinding
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicChartFragment : BaseFragment<FragmentMusicchartBinding>(R.layout.fragment_musicchart) {
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun init() {

        binding.apply {
            homeVM = homeViewModel
        }

        initPopularMusic()
    }

    private fun initPopularMusic() {
        homeViewModel.getMusics(null, null, null,"popular", null,0,0)

        val playListener: (MusicDetailResponse) -> (Unit) = {
            mainViewModel.insert(it.musicSeq)
            mainViewModel.successGetEvent=it.musicSeq
        }

        val moreListener: (MusicDetailResponse) -> (Unit) = {
            MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
                override fun clickDetail() {
                    val action =
                        MusicChartFragmentDirections.actionMusicChartFragmentToSongDetailFragment2(it.musicSeq)

                    findNavController().navigate(action)

                }

                override fun clickStudio() {
                    findNavController().navigate(R.id.action_musicChartFragment_to_userStudioFragment)
                }

                override fun clickReport() {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("http://pf.kakao.com/_lxeAjxj")
                    startActivity(i)
                }

            }).show(requireActivity().supportFragmentManager, "MoreDialog")
        }
        binding.rvMusicChartDetail.adapter = MusicChartAdapter(playListener, moreListener)
    }
}