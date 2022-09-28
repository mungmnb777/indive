package com.ssafy.indive.view.songdetail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentSongDetailBinding
import com.ssafy.indive.model.dto.Comment
import com.ssafy.indive.view.player.AddCommentFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongDetailFragment : BaseFragment<FragmentSongDetailBinding>(R.layout.fragment_song_detail) {

    private val songDetailViewModel: SongDetailViewModel by viewModels()

    var musicSeq = 0L

    override fun init() {

        musicSeq = arguments?.getLong("musicSeq")!!

        initMusicDetails()
        binding.apply {
            songdetailVM = songDetailViewModel
        }
        initCommentList()
        initClickListener()

    }

    private fun initMusicDetails() {
       songDetailViewModel.getMusicDetail(musicSeq)
    }

    private fun initCommentList() {
        songDetailViewModel.getMusicReply(musicSeq)
        binding.rvComment.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = CommentAdapter(object : CommentAdapter.CommentCLickListener {
            override fun clickEdit(comment: Comment) {
                showToast("수정")
            }

            override fun clickRemove(commentSeq: Long) {
                showToast("삭제")

            }

            override fun clickReport(commentSeq: Long) {
                showToast("신고")

            }

        })
    }

    private fun initClickListener() {
        binding.tvDetailLyricsExpand.setOnClickListener {
            binding.clSongLyricsChild.visibility = View.VISIBLE
            binding.tvDetailLyricsExpand.visibility = View.GONE
        }

        binding.tvDetailLyricsFold.setOnClickListener {
            binding.clSongLyricsChild.visibility = View.GONE
            binding.tvDetailLyricsExpand.visibility = View.VISIBLE
        }

        binding.tvAddComment.setOnClickListener {
            val bottomSheet = AddCommentFragment()
            bottomSheet.show(parentFragmentManager, AddCommentFragment.TAG)
        }
    }
}