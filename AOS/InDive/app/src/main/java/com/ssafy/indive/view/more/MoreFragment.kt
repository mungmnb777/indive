package com.ssafy.indive.view.more

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.fingerprint.FingerprintManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentMoreBinding
import com.ssafy.indive.utils.ABLE
import com.ssafy.indive.utils.DISABLE
import com.ssafy.indive.utils.FINGERPRINT_USE
import com.ssafy.indive.utils.JWT
import com.ssafy.indive.view.login.LoginActivity
import com.ssafy.indive.view.more.box.BoxActivity
import com.ssafy.indive.view.player.PlayerActivity
import com.ssafy.indive.view.player.PlayerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {

    private val moreViewModel by viewModels<MoreViewModel>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.R)
    override fun init() {
        binding.apply {
            moreVM = moreViewModel
        }
        initClickListener()
        initSwitch()
        getTokenBalanceOf()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initSwitch() {

        val isFingerChecked = sharedPreferences.getInt(FINGERPRINT_USE, DISABLE)

        binding.switchSettingFinger.isChecked = isFingerChecked == ABLE


        binding.switchSettingFinger.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                Log.d("MoreFragment_", "initSwitch: $isChecked")
//                val biometricManager = BiometricManager.from(requireActivity())
//
//                if (biometricManager.canAuthenticate(BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
//                    Log.d("MoreFragment_", "initSwitch: BIOMETRIC_ERROR_NONE_ENROLLED")
//                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                        putExtra(
//                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
//                        )
//                    }
//                   loginLauncher.launch(enrollIntent)
//                }else if(biometricManager.canAuthenticate(BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS){
//                    Log.d("MoreFragment_", "initSwitch: BIOMETRIC_SUCCESS")
//
//                }

                val fingerprintManager = FingerprintManagerCompat.from(requireContext())
                if (fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()) {
                    Log.d("MoreFragment_", "initSwitch: true ")
                    sharedPreferences.edit().putInt(FINGERPRINT_USE, ABLE).apply()
                } else {
                    Log.d("MoreFragment_", "initSwitch: false ")
                    val enrollIntent = Intent(Settings.ACTION_FINGERPRINT_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    startActivity(enrollIntent)
                }


            } else {
                sharedPreferences.edit().putInt(FINGERPRINT_USE, DISABLE).apply()
            }
        }
    }

    private fun initClickListener() {
        binding.apply {
            btnMyWallet.setOnClickListener {
                findNavController().navigate(R.id.action_moreFragment_to_myWalletFragment)
            }
            btnNftList.setOnClickListener {
                val intent = Intent(requireContext(), BoxActivity::class.java)
                startActivity(intent)
            }
            tvSettingMsg.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("http://pf.kakao.com/_lxeAjxj")
                startActivity(i)
            }
            tvSettingShield.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("https://sites.google.com/view/indive-terms")
                startActivity(i)
            }
            tvSettingLogout.setOnClickListener {
                sharedPreferences.edit().putString(JWT, "").apply()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

            btnBox.setOnClickListener {
                val intent = Intent(requireContext(), PlayerActivity::class.java)
                intent.putExtra("index", PlayerFragment.songPosition)
                intent.putExtra("class", "NowPlaying")
                intent.putExtra("type","moreBox")
                startActivity(intent)
            }
        }


    }

    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("MoreFragment_", "$it")

            if (it.resultCode == Activity.RESULT_OK) {
                Log.d("MoreFragment_", ": ")
            }
        }

    private fun getTokenBalanceOf(){
        moreViewModel.getTokenBalanceOf()
    }
}