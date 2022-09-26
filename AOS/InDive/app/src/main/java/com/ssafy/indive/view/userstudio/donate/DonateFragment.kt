package com.ssafy.indive.view.userstudio.donate

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentDonateBinding

class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {
    override fun init() {

        binding.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }
    }
}