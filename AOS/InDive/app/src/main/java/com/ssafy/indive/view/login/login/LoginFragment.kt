package com.ssafy.indive.view.login.login


import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainActivity
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentLoginBinding
import com.ssafy.indive.model.dto.MemberLogin
import com.ssafy.indive.utils.JWT
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val memberViewModel: MemberViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun init() {
        autoLogin()
        initClickListener()
        initViewModelCallback()
    }

    private fun autoLogin() {
        if (sharedPreferences.getString(JWT, "") != "") {
            Log.d(TAG, "autoLogin: ${sharedPreferences.getString(JWT, "")}")
            loginSuccess()
        }
    }

    private fun loginSuccess() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun initViewModelCallback() {
        memberViewModel.loginSuccess.observe(viewLifecycleOwner) {
            showToast(it)
            loginSuccess()
        }
        memberViewModel.loginFail.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    private fun initClickListener() {
        binding.apply {
            btnJoinSns.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_joinSnsFragment)
            }
            btnJoin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
            }
            btnLogin.setOnClickListener {
//                memberViewModel.memberLogin(MemberLogin(etId.text.toString(), etPass.text.toString()))
                memberViewModel.memberLogin(MemberLogin("1@1", "1"))
            }
        }
    }
}