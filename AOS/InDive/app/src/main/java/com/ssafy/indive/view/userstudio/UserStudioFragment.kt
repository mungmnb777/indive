package com.ssafy.indive.view.userstudio

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.MoreDialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentUserStudioBinding
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.USER
import com.ssafy.indive.view.genre.genrelist.GenreListAdapter
import com.ssafy.indive.view.genre.genrelist.GenreListFragmentDirections
import com.ssafy.indive.view.login.MemberViewModel
import com.ssafy.indive.view.mystudio.MyStudioFragmentDirections
import com.ssafy.indive.view.player.PlayerMoreDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "UserStudioFragment"

@AndroidEntryPoint
class UserStudioFragment : BaseFragment<FragmentUserStudioBinding>(R.layout.fragment_user_studio) {
    private val memberViewModel: MemberViewModel by viewModels()
    private val userStudioViewModel: UserStudioViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: UserStudioFragmentArgs by navArgs()
    private var notice: String = ""
    @Inject
    lateinit var sharedPreferences: SharedPreferences



    override fun init() {
        binding.apply {
            memberVM = memberViewModel
            userStudioVM = userStudioViewModel
        }
        initClickListener()
        initViewModel()
        initUserMusicList()
    }

    private fun initUserMusicList() {
        val playListener: (MusicDetailResponse) -> (Unit) = {
            mainViewModel.insert(it.musicSeq)
            mainViewModel.successGetEvent=it.musicSeq
        }

        val moreListener: (MusicDetailResponse) -> (Unit) = {
            PlayerMoreDialogFragment(object :
                PlayerMoreDialogFragment.PlayerMoreDialogClickListener {
                override fun clickDetail() {
                    val action =
                        UserStudioFragmentDirections.actionUserStudioFragmentToSongDetailFragment2(it.musicSeq)
                    findNavController().navigate(action)

                }

                override fun clickReport() {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("http://pf.kakao.com/_lxeAjxj")
                    startActivity(i)
                }

            }).show(parentFragmentManager, "MoreDialog")
        }

        binding.rvUserMusic.adapter = GenreListAdapter(playListener, moreListener)
    }

    private fun initViewModel() {
        memberViewModel.memberDetail(args.artistSeq)
        userStudioViewModel.getUserMusicList(args.artistSeq)
    }

    private fun initClickListener() {
        binding.apply {
            btnDonate.setOnClickListener {
                findNavController().navigate(UserStudioFragmentDirections.actionUserStudioFragmentToDonateFragment(args.artistSeq))
            }
            btnRanking.setOnClickListener {
                findNavController().navigate(R.id.action_userStudioFragment_to_rankingFragment)
            }
        }
    }
}