package com.ssafy.indive.view.genre


import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentGenreBinding

class GenreFragment : BaseFragment<FragmentGenreBinding>(R.layout.fragment_genre) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener() {

        binding.apply {
            cvRap.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("hiphop")
                findNavController().navigate(action)
            }
            cvBallad.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("ballad")
                findNavController().navigate(action)
            }
            cvNewAge.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("new-age")
                findNavController().navigate(action)
            }
            cvRAndB.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("rnb")
                findNavController().navigate(action)
            }
            cvJazz.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("jazz")
                findNavController().navigate(action)
            }
            cvDance.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("dance")
                findNavController().navigate(action)
            }
            cvFork.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("folk")
                findNavController().navigate(action)
            }
            cvAcoustic.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("acoustic")
                findNavController().navigate(action)
            }
            cvRock.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("rock")
                findNavController().navigate(action)
            }
            cvRockBallad.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("rock-ballad")
                findNavController().navigate(action)
            }
            cvEdm.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("edm")
                findNavController().navigate(action)
            }
            cvTrot.setOnClickListener {
                val action = GenreFragmentDirections.actionGenreFragmentToGenreListFragment("trot")
                findNavController().navigate(action)
            }
        }

    }
}