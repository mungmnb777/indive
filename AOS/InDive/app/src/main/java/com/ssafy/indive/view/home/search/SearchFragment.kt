package com.ssafy.indive.view.home.search

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentSearchBinding
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.USER
import com.ssafy.indive.view.home.HomeFragmentDirections
import com.ssafy.indive.view.home.HomeViewModel
import com.ssafy.indive.view.home.MusicChartAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun init() {
        binding.searchVM = searchViewModel
        initView()
        initClickListener()
        initAdapter()
    }

    private fun initView() {

        searchViewModel.isEmptyArtistList.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    tvSearchArtist.visibility = View.GONE
                    rvSearchArtist.visibility = View.GONE
                }
            } else {
                binding.apply {
                    tvSearchArtist.visibility = View.VISIBLE
                    rvSearchArtist.visibility = View.VISIBLE
                }
            }
        }
        searchViewModel.isEmptyMusicList.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    tvSearchSong.visibility = View.GONE
                    rvSearchSong.visibility = View.GONE
                }
            } else {
                binding.apply {
                    tvSearchSong.visibility = View.VISIBLE
                    rvSearchSong.visibility = View.VISIBLE
                }
            }
        }

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

            val memberSeq = it.memberSeq
            //유저 스튜디오로 넘기기
            val action =
                SearchFragmentDirections.actionSearchFragmentToUserStudioFragment(memberSeq)

            findNavController().navigate(action)
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
                val mySeq = sharedPreferences.getLong(USER, 0L)
                val memberSeq = it.artist.memberSeq

                Log.d("HomeFragment_", "mySeq : $mySeq")
                Log.d("HomeFragment_", "memberSeq : $memberSeq")

                if (mySeq == memberSeq) {

                    findNavController().navigate(R.id.action_searchFragment_to_myStudioFragment)
                } else {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToUserStudioFragment(memberSeq)

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

}