package com.ssafy.indive.view.home.musicchart

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMusicchartBinding
import com.ssafy.indive.utils.TAG
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.model.dto.Song
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.songdetail.SongDetailActivity

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
        homeViewModel.initPopularSongList()
        binding.rvMusicChartDetail.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        val playListener: (Song) -> (Unit) = {
            mainViewModel.play()
        }

        val moreListener: (Song) -> (Unit) = {
            MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
                override fun clickDetail() {
                    val intent = Intent(context, SongDetailActivity::class.java)
                    startActivity(intent)

                }

                override fun clickStudio() {
                    findNavController().navigate(R.id.action_musicChartFragment_to_userStudioFragment)
                }

                override fun clickReport() {

                }

            }).show(requireActivity().supportFragmentManager, "MoreDialog")
        }
        binding.rvMusicChartDetail.adapter = MusicChartAdapter(playListener, moreListener)
    }
}