package com.ssafy.indive.view.mystudio

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentMyStudioBinding

class MyStudioFragment : BaseFragment<FragmentMyStudioBinding>(R.layout.fragment_my_studio) {

    override fun init() {
        clickListener()
    }

    private fun clickListener() {
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_myStudioFragment_to_editProfileFragment)
        }
        binding.btnAddsong.setOnClickListener {
            findNavController().navigate(R.id.action_myStudioFragment_to_addSongFirstFragment)
        }
        binding.btnRanking.setOnClickListener {
            findNavController().navigate(R.id.action_myStudioFragment_to_rankingFragment)
        }
        binding.ivAddNft.setOnClickListener {
            val action = MyStudioFragmentDirections.actionMyStudioFragmentToAddRewardFragment(0)
            findNavController().navigate(action)
        }
    }
}