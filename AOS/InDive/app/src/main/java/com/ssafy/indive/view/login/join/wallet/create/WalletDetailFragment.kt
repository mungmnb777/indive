package com.ssafy.indive.view.login.join.wallet.create

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentWalletDetailBinding
import com.ssafy.indive.model.dto.MemberJoin
import dagger.hilt.android.AndroidEntryPoint
import com.ssafy.indive.view.login.MemberViewModel

@AndroidEntryPoint
class WalletDetailFragment: BaseFragment<FragmentWalletDetailBinding>(R.layout.fragment_wallet_detail) {

    private val walletDetailViewModel by viewModels<WalletDetailViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()

    private val args by navArgs<WalletDetailFragmentArgs>()

    override fun init() {
        binding.apply {
            walletDetailVM = walletDetailViewModel
        }

        initClickListener()
        initViewModelCallback()

        createWallet()
    }

    private fun initViewModelCallback(){
        walletDetailViewModel.transactionSuccess.observe(viewLifecycleOwner){
            memberViewModel.memberJoin(
                MemberJoin(
                    memberViewModel.email.value,
                    memberViewModel.password.value,
                    memberViewModel.nickname.value,
                    walletDetailViewModel.address.value,
                    "안녕하세요"
                )
            )
        }
    }

    private fun initClickListener(){
        binding.apply {
            btnDone.setOnClickListener {
                findNavController().navigate(R.id.action_walletDetailFragment_to_loginFragment)
            }
        }
    }

    private fun createWallet(){
        walletDetailViewModel.createWallet(args.password, memberViewModel.email.value)
    }
}