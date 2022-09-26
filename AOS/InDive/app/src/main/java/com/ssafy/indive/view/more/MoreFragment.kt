package com.ssafy.indive.view.more

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentMoreBinding

class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.btnMyWallet.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_myWalletFragment)
        }
        binding.btnNftList.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_donateListFragment)
        }
    }
}