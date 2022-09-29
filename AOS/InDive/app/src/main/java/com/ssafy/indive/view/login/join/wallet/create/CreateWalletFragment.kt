package com.ssafy.indive.view.login.join.wallet.create

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainActivity
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentCreateWalletBinding
import com.ssafy.indive.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateWalletFragment: BaseFragment<FragmentCreateWalletBinding>(R.layout.fragment_create_wallet) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            btnCreateWallet.setOnClickListener {
                val password = etPasswd.text.toString()
                if(password.isEmpty()){
                    showToast("비밀번호를 입력해주세요")
                } else {
                    val action = CreateWalletFragmentDirections.actionCreateWalletFragmentToWalletDetailFragment(password)
                    findNavController().navigate(action)
                }
            }
        }
    }

}