package com.ssafy.indive.view.login.join

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentJoinBinding
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join){

    private val memberViewModel: MemberViewModel by viewModels()

    override fun init() {
        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            btnJoin.setOnClickListener {
//                memberViewModel.memberJoin(MemberJoin("22@22", "22", "22", "22", "22" ))
                findNavController().navigate(R.id.action_joinFragment_to_walletFragment)
            }
        }
    }

}