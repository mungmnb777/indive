package com.ssafy.indive.view.login.login

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentLoginBinding
import retrofit2.Response

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun init() {


        binding.btnJoin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
        binding.btnJoinSns.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinSnsFragment)
        }

    }
}