package com.ssafy.indive.view.login

import com.ssafy.indive.base.BaseActivity
import android.content.Intent
import android.content.SharedPreferences
import com.ssafy.indive.MainActivity
import com.ssafy.indive.R
import com.ssafy.indive.databinding.ActivityMainBinding
import com.ssafy.indive.utils.JWT
//import com.ssafy.runwithme.databinding.ActivityMainBinding
//import com.ssafy.runwithme.utils.JWT
//import com.ssafy.runwithme.utils.PERMISSION_OK
//import com.ssafy.runwithme.view.permission.PermissionActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : com.ssafy.indive.base.BaseActivity<ActivityMainBinding>(R.layout.activity_login) {

//    @Inject
//    lateinit var sharedPref: SharedPreferences

    override fun init() {
//        if(!sharedPref.getBoolean(PERMISSION_OK, false)){
//            startActivity(Intent(this, PermissionActivity::class.java))
//            finish()
//        }
//
//        autoLogin()
    }

//    private fun autoLogin(){
//        val jwt = sharedPref.getString(JWT, "")
//
//        if(jwt != ""){
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
}