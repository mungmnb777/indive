package com.ssafy.indive.view.genre.genrelist

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentGenreListBinding

class GenreListFragment : BaseFragment<FragmentGenreListBinding>(R.layout.fragment_genre_list)  {

    override fun init() {

        initToolBar()

    }

    private fun initToolBar() {
        val title = arguments?.getString("title")
        binding.tvToolbarTitle.text = title
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }
}