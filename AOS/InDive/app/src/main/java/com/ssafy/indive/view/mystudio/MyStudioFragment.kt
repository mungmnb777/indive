package com.ssafy.indive.view.mystudio

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentMyStudioBinding
import com.ssafy.indive.view.userstudio.donate.FingerPrintDialog

class MyStudioFragment : BaseFragment<FragmentMyStudioBinding>(R.layout.fragment_my_studio) {

    override fun init() {
        initClickListener()
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
                NoticeDialog().show(parentFragmentManager, "")
            }
        }

    }
}