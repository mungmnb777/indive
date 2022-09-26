package com.ssafy.indive.view.more

import android.content.SharedPreferences
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentMoreBinding
import com.ssafy.indive.utils.ABLE
import com.ssafy.indive.utils.DISABLE
import com.ssafy.indive.utils.FINGERPRINT_USE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun init() {
        initClickListener()
        initSwitch()
    }

    private fun initSwitch() {

        val isFingerChecked = sharedPreferences.getInt(FINGERPRINT_USE, DISABLE)

        binding.switchSettingFinger.isChecked = isFingerChecked == ABLE


        binding.switchSettingFinger.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked){
                sharedPreferences.edit().putInt(FINGERPRINT_USE, ABLE).apply()
            }else{
                sharedPreferences.edit().putInt(FINGERPRINT_USE, DISABLE).apply()
            }
        }
    }

    private fun initClickListener() {
        binding.btnMyWallet.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_myWalletFragment)
        }
        binding.btnNftList.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_donateListFragment)
        }



    }
}