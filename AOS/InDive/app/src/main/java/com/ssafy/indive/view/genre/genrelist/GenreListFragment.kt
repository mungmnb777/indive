package com.ssafy.indive.view.genre.genrelist

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentGenreListBinding
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.USER
import com.ssafy.indive.view.home.HomeFragmentDirections
import com.ssafy.indive.view.player.PlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenreListFragment : BaseFragment<FragmentGenreListBinding>(R.layout.fragment_genre_list) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val genreListViewModel: GenreListViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val args by navArgs<GenreListFragmentArgs>()
    private var genre = ""

    override fun init() {

        genre = args.genre

        var genreName = ""
        when (genre) {
            "hiphop" -> {
                genreName = "랩/힙합"
            }
            "ballad" -> {
                genreName = "발라드"
            }
            "new-age" -> {
                genreName = "뉴에이지/연주곡"
            }
            "rnb" -> {
                genreName = "R&B/소울"
            }
            "jazz" -> {
                genreName = "재즈"
            }
            "dance" -> {
                genreName = "댄스"
            }
            "folk" -> {
                genreName = "포크/블루스"
            }
            "acoustic" -> {
                genreName = "어쿠스틱"
            }
            "rock" -> {
                genreName = "락/메탈"
            }
            "rock-ballad" -> {
                genreName = "락발라드"
            }
            "edm" -> {
                genreName = "EDM"
            }
            "trot" -> {
                genreName = "트로트"
            }
        }

        binding.apply {
            genreVM = genreListViewModel
            tvToolbarTitle.text = genreName
        }

        initClickListener()
        initViewModelCallback()
        initGenreList()

    }

    private fun initViewModelCallback() {
        genreListViewModel.getGenreList(genre)

    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initGenreList() {

        val playListener: (MusicDetailResponse) -> (Unit) = {
            mainViewModel.insert(it.musicSeq)
            mainViewModel.successGetEvent = it.musicSeq
        }

        val moreListener: (MusicDetailResponse) -> (Unit) = {
            MoreDialogFragment(object : MoreDialogFragment.MoreDialogClickListener {
                override fun clickDetail() {
                    val action =
                        GenreListFragmentDirections.actionGenreListFragmentToSongDetailFragment2(it.musicSeq)
                    findNavController().navigate(action)
                }

                override fun clickStudio() {
                    val mySeq = sharedPreferences.getLong(USER, 0L)
                    val memberSeq = it.artist.memberSeq
                    if (mySeq == memberSeq) {
                        findNavController().navigate(R.id.action_genreListFragment_to_myStudioFragment)
                    } else {
                        val action =
                            GenreListFragmentDirections.actionGenreListFragmentToUserStudioFragment(
                                memberSeq
                            )

                        findNavController().navigate(action)
                    }


                }

                override fun clickReport() {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("http://pf.kakao.com/_lxeAjxj")
                    startActivity(i)
                }

            }).show(requireActivity().supportFragmentManager, "MoreDialog")
        }
        binding.rvGenreList.adapter = GenreListAdapter(playListener, moreListener)
    }
}