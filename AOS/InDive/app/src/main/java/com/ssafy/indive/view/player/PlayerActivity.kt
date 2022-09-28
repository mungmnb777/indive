package com.ssafy.indive.view.player

import androidx.activity.viewModels
import com.ssafy.indive.MainActivity
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseActivity
import com.ssafy.indive.databinding.ActivityPlayer2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerActivity : BaseActivity<ActivityPlayer2Binding>(R.layout.activity_player2) {
    private val vm: MainViewModel by viewModels()
    override fun init() {
        showToast("${MainActivity.playList.size}")
    }


}