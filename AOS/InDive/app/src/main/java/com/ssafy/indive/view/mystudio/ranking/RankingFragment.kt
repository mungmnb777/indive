package com.ssafy.indive.view.mystudio.ranking

import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentRankingBinding

class RankingFragment : BaseFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    override fun init() {
        initClickListener()
    }
    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}