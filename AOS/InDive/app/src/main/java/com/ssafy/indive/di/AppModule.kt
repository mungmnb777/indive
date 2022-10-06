package com.ssafy.indive.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ssafy.indive.db.PlayListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Room DI
    @Singleton
    @Provides
    fun providePlayListDatabase(app: Application) =
        Room.databaseBuilder(app, PlayListDatabase::class.java, "Indive_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideIndiveDao(db: PlayListDatabase) = db.playListDao()

    //SharedPreferences DI
    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application) =
        app.getSharedPreferences("app_preference", Context.MODE_PRIVATE)!!


}