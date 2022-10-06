package com.ssafy.indive.view.more.box

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.indive.R
import com.ssafy.indive.databinding.ActivityBoxBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BoxActivity : AppCompatActivity() {

    private val boxViewModel by viewModels<BoxViewModel>()
    private lateinit var adapter : BoxAdapter
    private lateinit var binding : ActivityBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 캡쳐 방지
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_box)
        binding.lifecycleOwner = this

        adapter = BoxAdapter()
        binding.apply {
            boxVM = boxViewModel
            rvBoxList.adapter = adapter
            rvBoxList.layoutManager = GridLayoutManager(this@BoxActivity, 2)
        }

        initViewModelCallback()
    }

    private fun initViewModelCallback(){
        boxViewModel.getNFTTokenList()
    }
}