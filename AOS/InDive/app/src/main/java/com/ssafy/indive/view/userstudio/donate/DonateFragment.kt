package com.ssafy.indive.view.userstudio.donate

import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentDonateBinding
import com.ssafy.indive.utils.ABLE
import com.ssafy.indive.utils.DISABLE
import com.ssafy.indive.utils.FINGERPRINT_USE
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    @Inject
    lateinit var sharedPreference: SharedPreferences

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun init() {
        initBioMetric()
        initFingerPrintAuth()
        initClickListener()

    }

    private fun initClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
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