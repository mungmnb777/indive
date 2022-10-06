package com.ssafy.indive.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.ssafy.indive.view.player.PlayerFragment

class ServiceTest : ServiceConnection {
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        Log.d("ServiceTest_", "onServiceConnected: ")
        val binder = p1 as MusicService.MyBinder
        PlayerFragment.musicService = binder.currentService()
        PlayerFragment.musicService!!.createExoPlayer()

    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        PlayerFragment.musicService = null
    }
}