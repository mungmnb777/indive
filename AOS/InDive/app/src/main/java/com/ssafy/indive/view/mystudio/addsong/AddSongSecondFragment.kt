package com.ssafy.indive.view.mystudio.addsong

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongSecondBinding

class AddSongSecondFragment : BaseFragment<FragmentAddSongSecondBinding>(R.layout.fragment_add_song_second) {
    override fun init() {
        clickListnener()
    }

    private fun clickListnener() {
        binding.btnAddsongSecond.setOnClickListener {
            findNavController().navigate(R.id.action_addSongSecondFragment_to_addSongThirdFragment)
        }
    }
}