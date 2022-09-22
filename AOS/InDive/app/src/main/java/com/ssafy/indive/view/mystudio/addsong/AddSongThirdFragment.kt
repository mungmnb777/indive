package com.ssafy.indive.view.mystudio.addsong

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongThirdBinding

class AddSongThirdFragment : BaseFragment<FragmentAddSongThirdBinding>(R.layout.fragment_add_song_third) {
    override fun init() {
        clickListnener()
    }

    private fun clickListnener() {
        binding.btnAddsongThird.setOnClickListener {
            findNavController().navigate(R.id.action_addSongThirdFragment_to_myStudioFragment)
        }
    }
}