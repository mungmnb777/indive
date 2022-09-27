package com.ssafy.indive.view.login.join.wallet

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainActivity
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentWalletBinding


class WalletFragment : BaseFragment<FragmentWalletBinding>(R.layout.fragment_wallet) {

    override fun init() {
        initClickListener()
    }



    private fun initClickListener(){
        binding.apply {
            tvWalletFragment.setOnClickListener {
                findNavController().navigate(R.id.action_walletFragment_to_createWalletFragment)
            }
        }
    }

}