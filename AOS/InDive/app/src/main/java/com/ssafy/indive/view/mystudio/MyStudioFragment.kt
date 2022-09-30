package com.ssafy.indive.view.mystudio

import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.binding.RecyclerBinding.bindImage
import com.ssafy.indive.databinding.FragmentMyStudioBinding
import com.ssafy.indive.model.dto.Notice
import com.ssafy.indive.utils.MEMBER_FOOTER
import com.ssafy.indive.utils.MEMBER_HEADER
import com.ssafy.indive.utils.USER
import com.ssafy.indive.view.login.MemberViewModel
import com.ssafy.indive.view.userstudio.donate.FingerPrintDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MyStudioFragment"

@AndroidEntryPoint
class MyStudioFragment : BaseFragment<FragmentMyStudioBinding>(R.layout.fragment_my_studio) {
    private val memberViewModel: MemberViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var notice: String = ""
    private var listener: (String) -> (Unit) = {
        memberViewModel.writeNotice(sharedPreferences.getLong(USER, 0), Notice(it))
    }

    override fun init() {
        binding.apply {
            memberVM = memberViewModel
        }

        initClickListener()
        initViewModel()
        initViewModelCallback()
    }

    private fun initViewModel() {
        memberViewModel.memberDetail(sharedPreferences.getLong(USER, 0))
    }

    private fun initViewModelCallback() {
        memberViewModel.notice.observe(viewLifecycleOwner) {
            if (it != null) {
                notice = it
            }

        }
        memberViewModel.noticeSuccess.observe(viewLifecycleOwner) {
            initViewModel()
        }
    }

    private fun initClickListener() {
        binding.apply {
            btnEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_myStudioFragment_to_editProfileFragment)
            }
            btnAddsong.setOnClickListener {
                findNavController().navigate(R.id.action_myStudioFragment_to_addSongFirstFragment)
            }
            btnRanking.setOnClickListener {
                findNavController().navigate(R.id.action_myStudioFragment_to_rankingFragment)
            }
            ivAddNft.setOnClickListener {
                val action = MyStudioFragmentDirections.actionMyStudioFragmentToAddRewardFragment(0)
                findNavController().navigate(action)
            }
            ivEditNotice.setOnClickListener {
                NoticeDialog(notice, listener).show(parentFragmentManager, "")
            }
        }

    }
}