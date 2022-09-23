package com.ssafy.indive.view.mystudio.addsong

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongFirstBinding

class AddSongFirstFragment : BaseFragment<FragmentAddSongFirstBinding>(R.layout.fragment_add_song_first) {
    override fun init() {
        clickListnener()
    }

    private fun clickListnener() {
        binding.apply {
            btnAddsongFirst.setOnClickListener {
                findNavController().navigate(R.id.action_addSongFirstFragment_to_addSongSecondFragment)
            }
            btnSongGenre.setOnClickListener {
                GenreDialog(requireContext()).let {
                    it.show(parentFragmentManager, null)
                    it.onDismissDialogListener = object : GenreDialog.OnDismissDialogListener{
                        @SuppressLint("ResourceAsColor")
                        override fun onDismiss(Genre: String) {
                            btnSongGenre.setBackgroundResource(R.drawable.btn_round_border_main_color)
                            btnSongGenre.text = Genre
                            btnSongGenre.setTextColor(R.color.white)
                        }

                    }
                }
            }
        }

    }
}