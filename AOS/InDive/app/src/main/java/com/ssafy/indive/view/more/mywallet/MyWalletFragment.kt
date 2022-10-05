package com.ssafy.indive.view.more.mywallet

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMyWalletBinding
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWalletFragment : BaseFragment<FragmentMyWalletBinding>(R.layout.fragment_my_wallet) {

    private val myWalletViewModel by viewModels<MyWalletViewModel>()

    override fun init() {
        binding.apply {
            myWalletVM = myWalletViewModel
        }

        initViewModelCallback()
        initDonationHistory()
    }

    private fun initViewModelCallback(){
        myWalletViewModel.getTokenBalanceOf()
        myWalletViewModel.getHistory()
    }

    private fun initDonationHistory(){
        binding.rvDonationList.adapter = MyWalletAdapter()
    }
}