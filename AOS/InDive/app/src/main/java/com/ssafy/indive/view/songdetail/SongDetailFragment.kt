package com.ssafy.indive.view.songdetail

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentSongDetailBinding
import com.ssafy.indive.model.response.ReplyResponse
import com.ssafy.indive.utils.TAG
import com.ssafy.indive.utils.USER
import com.ssafy.indive.view.loading.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SongDetailFragment : BaseFragment<FragmentSongDetailBinding>(R.layout.fragment_song_detail) {

    private val songDetailViewModel: SongDetailViewModel by viewModels()
    private val args by navArgs<SongDetailFragmentArgs>()
    private lateinit var loadingDialog: LoadingDialog
    lateinit var replyAdapter: ReplyAdapter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    var musicSeq = 0L

    override fun init() {

        binding.apply {
            songdetailVM = songDetailViewModel
        }
        musicSeq = args.musicSeq
        loadingDialog = LoadingDialog(requireContext())

        initMusicDetails()
        initReplyList()
        initClickListener()
        initObserver()

    }

    private fun initObserver() {
        songDetailViewModel.addReplySuccess.observe(viewLifecycleOwner) {
            if (it == "등록 성공") {
                songDetailViewModel.getMusicReply(musicSeq)
                loading()
                showToast("댓글이 등록되었습니다.")
            }
        }

        songDetailViewModel.modifySuccess.observe(viewLifecycleOwner) {
            if (it == "수정 성공") {
                songDetailViewModel.getMusicReply(musicSeq)
                loading()
                showToast("댓글이 수정되었습니다.")
            }
        }

        songDetailViewModel.deleteSuccess.observe(viewLifecycleOwner) {
            if (it == "삭제 성공") {
                songDetailViewModel.getMusicReply(musicSeq)
                loading()
                showToast("댓글이 삭제되었습니다.")
            }
        }

        lifecycleScope.launch {
            songDetailViewModel.musicReplyList.collectLatest {
                Log.d(TAG, "initObserver: $it ")
                replyAdapter.submitList(it)
            }
        }
    }

    private fun initMusicDetails() {
        songDetailViewModel.getMusicDetail(musicSeq)
    }

    private fun initReplyList() {

        songDetailViewModel.getMusicReply(musicSeq)

        val listener = object : ReplyAdapter.ReplyCLickListener {
            override fun clickEdit(reply: ReplyResponse) {

                val bottomSheet = AddCommentFragment {
                    songDetailViewModel.modifyReply(musicSeq, it, reply.replySeq)
                }
                bottomSheet.content = reply.content
                bottomSheet.show(parentFragmentManager, AddCommentFragment.TAG)

            }

            override fun clickRemove(replySeq: Long) {
                songDetailViewModel.deleteMusicReply(musicSeq, replySeq)
            }

            override fun clickReport(replySeq: Long) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("http://pf.kakao.com/_lxeAjxj")
                startActivity(i)
            }

        }
        replyAdapter = ReplyAdapter(listener, sharedPreferences.getLong(USER, 0L))

        binding.rvComment.adapter = replyAdapter
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
            val bottomSheet = AddCommentFragment {
                songDetailViewModel.addReply(musicSeq, it)
            }
            bottomSheet.show(parentFragmentManager, AddCommentFragment.TAG)
        }
    }

    private fun loading() {
        loadingDialog.show()
        // 로딩이 진행되지 않았을 경우
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }
    }
}