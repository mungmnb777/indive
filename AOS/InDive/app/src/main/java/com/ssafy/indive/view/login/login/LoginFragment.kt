package com.ssafy.indive.view.login.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentLoginBinding
import com.ssafy.indive.model.dto.MemberLogin
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val memberViewModel: MemberViewModel by viewModels()

    override fun init() {
        binding.apply {
            btnJoinSns.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_joinSnsFragment)
            }
            btnJoin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
            }
            btnLogin.setOnClickListener {
                memberViewModel.memberLogin(MemberLogin("11@11", "11"))
            }
        }
    }



}