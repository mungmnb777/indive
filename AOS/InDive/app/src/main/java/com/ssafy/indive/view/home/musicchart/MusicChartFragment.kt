package com.ssafy.indive.view.home.musicchart

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMusicchartBinding
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.player.PlayerActivity
import com.ssafy.indive.view.songdetail.SongDetailActivity
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
//        homeViewModel.initPopularSongList()
        binding.rvMusicChartDetail.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        val playListener: (Music) -> (Unit) = {
            mainViewModel.insert()
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("class","HomeFragment")
            startActivity(intent)
        }

        val moreListener: (Music) -> (Unit) = {
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