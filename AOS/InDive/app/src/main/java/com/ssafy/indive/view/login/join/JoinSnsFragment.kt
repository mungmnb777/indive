package com.ssafy.indive.view.login.join

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentJoinSnsBinding

class JoinSnsFragment : BaseFragment<FragmentJoinSnsBinding>(R.layout.fragment_join_sns) {

    override fun init() {
        binding.tvJoinSnsFragment.setOnClickListener {
            findNavController().navigate(R.id.action_joinSnsFragment_to_walletFragment)
        }
    }

}