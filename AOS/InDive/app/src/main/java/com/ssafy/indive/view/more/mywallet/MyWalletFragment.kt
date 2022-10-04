package com.ssafy.indive.view.more.mywallet

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentMyWalletBinding
import com.ssafy.indive.view.login.MemberViewModel

class MyWalletFragment : BaseFragment<FragmentMyWalletBinding>(R.layout.fragment_my_wallet) {

    private val myWalletViewModel by viewModels<MyWalletViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun init() {
        binding.apply {
            myWalletVM = myWalletViewModel
        }
    }

}