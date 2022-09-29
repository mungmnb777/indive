package com.ssafy.indive.view.login.join.wallet.create

import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentCreateWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadWalletFragment: BaseFragment<FragmentCreateWalletBinding>(R.layout.fragment_load_wallet) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
        }
    }

}