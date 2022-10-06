package com.ssafy.indive.view.login.join.wallet.create

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentLoadWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadWalletFragment : BaseFragment<FragmentLoadWalletBinding>(R.layout.fragment_load_wallet) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            btnLoadWallet.setOnClickListener {
                val privateKey = etPrivateKey.text.toString()
                if (privateKey.isEmpty()) {
                    showToast("개인키를 입력해주세요")
                } else {
                    val action =
                        LoadWalletFragmentDirections.actionLoadWalletFragmentToWalletDetailFragment(
                            "Load",
                            privateKey
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

}