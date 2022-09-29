package com.ssafy.indive.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

@HiltAndroidApp
class ApplicationClass : Application() {

    companion object{
        var appContext : Context? = null
        const val CHANNEL_ID = "channel1"
        const val PLAY = "play"
        const val NEXT = "next"
        const val PREV = "previous"
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CHANNEL_ID,"Now Playing Song", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "This is a important channle for showing song!!"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE)as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Provider 변경
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }
}