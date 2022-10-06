package com.ssafy.indive.view.more.box

import androidx.fragment.app.viewModels
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentBoxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoxFragment : BaseFragment<FragmentBoxBinding>(R.layout.fragment_box) {

    private val boxViewModel by viewModels<BoxViewModel>()
    private lateinit var adapter : BoxAdapter

    override fun init() {
        adapter = BoxAdapter()
        binding.apply {
            boxVM = boxViewModel
            rvBoxList.adapter = adapter
        }

        initViewModelCallback()
    }

    private fun initViewModelCallback(){
        boxViewModel.getNFTTokenList()
    }
}