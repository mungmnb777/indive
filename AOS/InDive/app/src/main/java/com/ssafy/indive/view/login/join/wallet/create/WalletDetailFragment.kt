package com.ssafy.indive.view.login.join.wallet.create

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentWalletDetailBinding
import com.ssafy.indive.utils.TAG
import com.ssafy.indive.view.loading.LoadingDialog
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletDetailFragment: BaseFragment<FragmentWalletDetailBinding>(R.layout.fragment_wallet_detail) {

    private val walletDetailViewModel by viewModels<WalletDetailViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()

    private val args by navArgs<WalletDetailFragmentArgs>()

    private lateinit var clipboardManager: ClipboardManager
    private lateinit var loadingDialog: LoadingDialog

    override fun init() {
        binding.apply {
            walletDetailVM = walletDetailViewModel
        }

        initClickListener()
        initViewModelCallback()

        loadingDialog = LoadingDialog(requireContext())
        loading()
        
        when(args.action){
            "Create" -> createWallet()
            "Load" -> loadWallet()
        }
    }

    private fun initViewModelCallback(){
        walletDetailViewModel.walletSuccess.observe(viewLifecycleOwner){
//            memberViewModel.memberJoin(
//                MemberJoin(
//                    memberViewModel.email.value,
//                    memberViewModel.password.value,
//                    memberViewModel.nickname.value,
//                    walletDetailViewModel.address.value,
//                    "안녕하세요"
//                )
//            )
            loadingDialog.dismiss()
        }
    }

    private fun initClickListener(){
        binding.apply {
            ivCopyPrivateKey.setOnClickListener {
                copyPrivateKey()
            }

            tvPrivateKey.setOnClickListener {
                copyPrivateKey()
            }

            ivCopyAddress.setOnClickListener {
                copyAddress()
            }

            tvAddress.setOnClickListener {
                copyAddress()
            }

            btnDone.setOnClickListener {
                findNavController().navigate(R.id.action_walletDetailFragment_to_loginFragment)
            }
        }
    }

    private fun copyPrivateKey(){
        clipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("PrivateKey", walletDetailViewModel.privateKey.value)
        clipboardManager.setPrimaryClip(clipData)

        showToast("개인키가 클립보드에 복사되었습니다.")
    }

    private fun copyAddress(){
        clipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Address", walletDetailViewModel.address.value)
        clipboardManager.setPrimaryClip(clipData)

        showToast("지갑 주소가 클립보드에 복사되었습니다.")
    }

    private fun createWallet(){
        CoroutineScope(Dispatchers.IO).launch {
            walletDetailViewModel.createWallet(args.value, memberViewModel.email.value)
        }
    }

    private fun loadWallet(){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "loadWallet: ${args.value}")
            walletDetailViewModel.loadWallet(args.value, memberViewModel.email.value)
        }
    }

    private fun loading(){
        loadingDialog.show()
        // 로딩이 진행되지 않았을 경우
        CoroutineScope(Dispatchers.Main).launch {
            delay(10000)
            if(loadingDialog.isShowing){
                loadingDialog.dismiss()
            }
        }
    }
}