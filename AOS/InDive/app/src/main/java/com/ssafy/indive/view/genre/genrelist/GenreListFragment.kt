package com.ssafy.indive.view.genre.genrelist

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentGenreListBinding
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.view.player.PlayerActivity
import com.ssafy.indive.view.songdetail.SongDetailActivity

class GenreListFragment : BaseFragment<FragmentGenreListBinding>(R.layout.fragment_genre_list) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val genreListViewModel: GenreListViewModel by viewModels()

    override fun init() {
        binding.genreVM = genreListViewModel
        initToolBar()
        initGenreList()

    }


    private fun initToolBar() {
        val title = arguments?.getString("title")
        binding.tvToolbarTitle.text = title
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun initGenreList() {

//        genreListViewModel.getGenres()
        val playListener: (Music) -> (Unit) = {
            mainViewModel.insert()
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("class","HomeFragment")
            startActivity(intent)
        }

        val moreListener: (Music) -> (Unit) = {
            MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
                override fun clickDetail() {
                    val intent = Intent(context, SongDetailActivity::class.java)
                    startActivity(intent)

                }

                override fun clickStudio() {
                    findNavController().navigate(R.id.action_genreListFragment_to_userStudioFragment)
                }

                override fun clickReport() {

                }

            }).show(requireActivity().supportFragmentManager, "MoreDialog")
        }
        binding.rvGenreList.adapter = GenreListAdapter(playListener, moreListener)
    }
}