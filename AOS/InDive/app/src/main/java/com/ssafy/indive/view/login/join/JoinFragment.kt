package com.ssafy.indive.view.login.join

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentJoinBinding

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join){
    override fun init() {
        initClickListener()
    }

    fun initClickListener(){
        binding.tvJoinFragment.setOnClickListener {
            findNavController().navigate(R.id.action_joinFragment_to_walletFragment)
        }
    }

}