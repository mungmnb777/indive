package com.ssafy.indive.view.songdetail

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseActivity
import com.ssafy.indive.databinding.ActivitySongDetailBinding
import com.ssafy.indive.model.dto.Comment
import com.ssafy.indive.view.player.AddCommentFragment

class SongDetailActivity : BaseActivity<ActivitySongDetailBinding>(R.layout.activity_song_detail) {
    private val songDetailViewModel: SongDetailViewModel by viewModels()

    override fun init() {
        overridePendingTransition(R.anim.translate_left,R.anim.none)
        binding.songdetailVM = songDetailViewModel
        initCommentList()
        initClickListener()
    }

    private fun initCommentList() {
//        songDetailViewModel.getComments()
        binding.rvComment.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
            bottomSheet.show(supportFragmentManager, AddCommentFragment.TAG)
        }
    }
}