package com.ssafy.indive.view.userstudio.donate

import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentDonateBinding
import com.ssafy.indive.utils.ABLE
import com.ssafy.indive.utils.DISABLE
import com.ssafy.indive.utils.FINGERPRINT_USE
import com.ssafy.indive.view.loading.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    @Inject
    lateinit var sharedPreference: SharedPreferences

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val donateViewModel by viewModels<DonateViewModel>()
    private val args by navArgs<DonateFragmentArgs>()

    private var artistSeq = 0L
    private lateinit var loadingDialog: LoadingDialog


    override fun init() {

        loadingDialog = LoadingDialog(requireContext())

        initBioMetric()
        initFingerPrintAuth()
        initClickListener()
        initViewModelCallback()

        artistSeq = args.artistSeq

    }

    private fun initViewModelCallback(){

        donateViewModel.checkIsGetNFT(artistSeq)

        donateViewModel.successMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }

        lifecycleScope.launch {
            donateViewModel.priceToGetNFT.collectLatest {
                if(it <= 0){
                    binding.apply {
                        tvAlertNft.visibility = View.INVISIBLE
                    }
                }else{
                    binding.apply {
                        tvAlertNft.text = "$it IVE 이상 후원 시 NFT를 받을 수 있습니다."
                    }
                }
            }
        }
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnDonate.setOnClickListener {
                donateViewModel.putRewardNFT(artistSeq)
                loading()
            }
        }
    }

    private fun loading(){
        loadingDialog.show()
        // 로딩이 진행되지 않았을 경우
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            if(loadingDialog.isShowing){
                loadingDialog.dismiss()
            }
        }
    }

    private fun initFingerPrintAuth() {
        if (sharedPreference.getInt(FINGERPRINT_USE, DISABLE) == 1) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            FingerPrintDialog().show(parentFragmentManager, "FingerPrintDialog")
        }
    }


    private fun initBioMetric() {
        executor = ContextCompat.getMainExecutor(requireActivity())
        biometricPrompt = BiometricPrompt(
            requireActivity(),
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    showToast("지문 인증 성공")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    findNavController().popBackStack()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("지문 인증 실패")

                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("지문 인증")
            .setNegativeButtonText("취소")
            .build()
    }
}