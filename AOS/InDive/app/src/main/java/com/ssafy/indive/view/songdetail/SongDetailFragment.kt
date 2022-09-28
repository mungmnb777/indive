package com.ssafy.indive.view.songdetail

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentSongDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongDetailFragment : BaseFragment<FragmentSongDetailBinding>(R.layout.fragment_song_detail) {

    private val songDetailViewModel: SongDetailViewModel by viewModels()

    var musicSeq = 0L

    override fun init() {
        musicSeq = arguments?.getLong("musicSeq")!!

        binding.songdetailVM = songDetailViewModel

    }
}