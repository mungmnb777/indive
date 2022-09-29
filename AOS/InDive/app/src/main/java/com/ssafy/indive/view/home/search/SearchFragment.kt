package com.ssafy.indive.view.home.search

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentSearchBinding
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.view.home.HomeFragmentDirections
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun init() {
        binding.searchVM = searchViewModel
        initClickListener()
        initAdapter()
    }

    private fun initClickListener() {
        binding.ivSearch.setOnClickListener {
            if (binding.etSearch.text.toString() == "") {
                showToast("검색어를 입력해주세요")
                return@setOnClickListener
            }

            val searchText = binding.etSearch.text.toString()
            searchViewModel.getMusics(searchText)

        }
    }

    private fun initAdapter() {
        binding.rvSearchArtist.adapter = SearchArtistAdapter {
            //유저 스튜디오로 넘기기
        }

        binding.rvSearchSong.adapter = MusicChartAdapter(playListener, moreListener)
    }

    private val playListener: (MusicDetailResponse) -> (Unit) = {
        mainViewModel.insert(it.musicSeq)
        mainViewModel.successGetEvent = it.musicSeq
    }

    private val moreListener: (MusicDetailResponse) -> (Unit) = {
        MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
            override fun clickDetail() {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToSongDetailFragment2(it.musicSeq)

                findNavController().navigate(action)
            }

            override fun clickStudio() {
                findNavController().navigate(R.id.action_searchFragment_to_userStudioFragment)
            }

            override fun clickReport() {

            }

        }).show(requireActivity().supportFragmentManager, "MoreDialog")
    }

}