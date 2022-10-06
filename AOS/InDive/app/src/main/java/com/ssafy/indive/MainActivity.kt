package com.ssafy.indive

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.indive.base.BaseActivity
import com.ssafy.indive.databinding.ActivityMainBinding
import com.ssafy.indive.model.dto.PlayListMusic
import com.ssafy.indive.utils.*
import com.ssafy.indive.view.player.PlayerActivity
import com.ssafy.indive.view.userstudio.donate.FingerPrintDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()

    lateinit var navController: NavController
    @Inject
    lateinit var sharePref : SharedPreferences

    companion object {
        lateinit var playList: MutableList<PlayListMusic>
        var successQRScanMsg = ""
    }

    override fun init() {
        playList = mutableListOf()
        initNavigation()
        initObserve()
        checkMediaPermission()
//        showFingerPrintDialog()
    }

//    private fun showFingerPrintDialog() {
//        if (sharePref.getInt(FINGERPRINT_USE, DISABLE) != 1) {
//            FingerPrintDialog().show(supportFragmentManager, "FingerPrintDialog")
//        }
//    }

    private fun checkMediaPermission() {

        CoroutineScope(Dispatchers.Main).launch {
            TedPermission.create()
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("[설정] -> [권한] -> [파일 및 미디어] -> 허용")
                .check()

        }
    }

    private fun initObserve() {
        mainViewModel.getAll()
        lifecycleScope.launch {
            mainViewModel.playList.collectLatest { playListEntity ->
                if (playListEntity.isNotEmpty()) {
                    playList = mutableListOf()
                    playListEntity.forEach {
                        playList.add(it.mapper())
                    }

                    Log.d(TAG, "initObserve@: ${mainViewModel.successGetEvent}")
                    if (mainViewModel.successGetEvent != 0L) {
                        val musicSeq = mainViewModel.successGetEvent
                        mainViewModel.successGetEvent = 0L

                        val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                        intent.putExtra("class", "HomeFragment")
                        intent.putExtra("musicSeq", musicSeq)
                        startActivity(intent)

                    }
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
                || destination.id == R.id.myStudioFragment || destination.id == R.id.moreFragment
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



