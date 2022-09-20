package com.ssafy.indive

import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import com.ssafy.indive.base.BaseActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ssafy.indive.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var navController: NavController

    override fun init() {

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            // 바텀 네비게이션이 표시되는 Fragment
//            if(destination.id == R.id.HomeFragment || destination.id == R.id.MyPageFragment){
//                if(binding.expandableBottomBar.visibility == View.GONE) {
//                    binding.apply {
//                        expandableBottomBar.visibility = View.VISIBLE
//                        bottomAppBar.visibility = View.VISIBLE
//                        floatingActionButton.visibility = View.VISIBLE
//                        view.visibility = View.VISIBLE
//                    }
//                }
//            }
//            // 바텀 네비게이션이 표시되지 않는 Fragment
//            else{
//                if(binding.expandableBottomBar.visibility == View.VISIBLE) {
//                    binding.apply {
//                        expandableBottomBar.visibility = View.GONE
//                        bottomAppBar.visibility = View.GONE
//                        floatingActionButton.visibility = View.GONE
//                        view.visibility = View.GONE
//                    }
//                }
//            }
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


