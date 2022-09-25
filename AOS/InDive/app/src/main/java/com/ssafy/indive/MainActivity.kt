package com.ssafy.indive

import android.Manifest
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.indive.base.BaseActivity
import com.ssafy.indive.databinding.ActivityMainBinding
import com.ssafy.indive.model.dto.PlayListMusic
import com.ssafy.indive.utils.mapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()

    private var READ_EXTERNAL_STORAGE = 0

    lateinit var navController: NavController

    companion object {
        lateinit var playList: MutableList<PlayListMusic>
    }

    override fun init() {
        playList = mutableListOf()
        initNavigation()
        initObserve()
        checkMediaPermission()
    }

    private fun checkMediaPermission() {

        CoroutineScope(Dispatchers.Main).launch {
            TedPermission.create()
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("[설정] -> [권한] -> [파일 및 미디어] -> 허용")
                .check()

        }
    }

    private fun initObserve() {
        mainViewModel.playList.observe(this) { playListEntity ->
            Log.d("MainActivity_", "initObserve: $playListEntity")
            if (mainViewModel.playList.value != null) {
                if (mainViewModel.playList.value!!.isNotEmpty()) {
                    playList = mutableListOf()
                    playListEntity.forEach {
                        playList.add(it.mapper())
                    }
                } else {
                    playList = mutableListOf()

                }
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 바텀 네비게이션이 표시되는 Fragment
            if (destination.id == R.id.homeFragment || destination.id == R.id.genreFragment
                || destination.id == R.id.myStudioFragment ||destination.id == R.id.moreFragment
                ) {
                if (binding.bottomNav.visibility == View.GONE) {
                    binding.apply {
                        bottomNav.visibility = View.VISIBLE
                        nowPlayingContainer.visibility = View.VISIBLE
                    }
                }
            }
            // 바텀 네비게이션이 표시되지 않는 Fragment
            else {
                if (binding.bottomNav.visibility == View.VISIBLE) {
                    binding.apply {
                        bottomNav.visibility = View.GONE
                        nowPlayingContainer.visibility = View.GONE
                    }
                }
            }
        }
    }

    //     홈 화면에서 뒤로가기 2번 클릭 시 종료
    var waitTime = 0L
    override fun onBackPressed() {

        if (navController.currentDestination?.id == R.id.homeFragment) {
            if (System.currentTimeMillis() - waitTime >= 2000) {
                waitTime = System.currentTimeMillis()
                showToast("뒤로가기를 한 번 더 누르시면 종료됩니다")
            } else {
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }
}



