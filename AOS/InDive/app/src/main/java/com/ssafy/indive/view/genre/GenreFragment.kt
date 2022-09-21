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
                val bundle = bundleOf("title" to "랩/힙합")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvBallad.setOnClickListener {
                val bundle = bundleOf("title" to "발라드")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvNewAge.setOnClickListener {
                val bundle = bundleOf("title" to "뉴에이지/연주곡")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvRAndB.setOnClickListener {
                val bundle = bundleOf("title" to "R&B/소울")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvJazz.setOnClickListener {
                val bundle = bundleOf("title" to "재즈")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvDance.setOnClickListener {
                val bundle = bundleOf("title" to "댄스")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvFork.setOnClickListener {
                val bundle = bundleOf("title" to "포크/블루스")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvAcoustic.setOnClickListener {
                val bundle = bundleOf("title" to "어쿠스틱")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvRock.setOnClickListener {
                val bundle = bundleOf("title" to "락/메탈")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvRockBallad.setOnClickListener {
                val bundle = bundleOf("title" to "락발라드")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvEdm.setOnClickListener {
                val bundle = bundleOf("title" to "EDM")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
            cvTrot.setOnClickListener {
                val bundle = bundleOf("title" to "트로트")
                findNavController().navigate(R.id.action_genreFragment_to_genreListFragment, bundle)
            }
        }

    }
}