package com.ssafy.indive.view.mystudio.ranking

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentRankingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val rankingViewModel: RankingViewModel by viewModels()
    private val args: RankingFragmentArgs by navArgs()

    override fun init() {
        binding.apply {
            rankingVM = rankingViewModel
        }

        initRankingList()
        initAdapter()
        initClickListener()
    }

    private fun initRankingList(){
        rankingViewModel.getRankingList(args.address)
    }

    private fun initAdapter(){
        binding.rvRanking.adapter = RankingAdapter()
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}