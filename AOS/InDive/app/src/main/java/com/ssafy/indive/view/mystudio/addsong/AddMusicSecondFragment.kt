package com.ssafy.indive.view.mystudio.addsong

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMusicSecondFragment :
    BaseFragment<FragmentAddSongSecondBinding>(R.layout.fragment_add_song_second) {

    private val addMusicViewModel: AddMusicViewModel by activityViewModels()

    override fun init() {
        binding.apply {
            addSongVM = addMusicViewModel
        }
        clickListener()
    }

    private fun clickListener() {
        binding.btnAddsongSecond.setOnClickListener {

//            if (checkEmpty()) {
            findNavController().navigate(R.id.action_addSongSecondFragment_to_addSongThirdFragment)
//            }
        }
    }

    private fun checkEmpty(): Boolean {

        if (addMusicViewModel.composer.value == "") {
            showToast("작곡가를 입력해 주세요")
            return false
        }

        return true

    }
}