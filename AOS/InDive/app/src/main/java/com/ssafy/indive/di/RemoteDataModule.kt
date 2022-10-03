package com.ssafy.indive.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.indive.api.MemberManagerApi
import com.ssafy.indive.api.MusicManagerApi
import com.ssafy.indive.utils.BASE_URL
import com.ssafy.indive.utils.XAccessTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {


    // HttpLoggingInterceptor DI
    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //OkHttpClient DI
    @Provides
    @Singleton
    fun provideOkHttpClient(
        xAccessTokenInterceptor: XAccessTokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(xAccessTokenInterceptor)
            .build()
    }

    // Gson DI
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    //Retrofit DI
    @Provides
    @Singleton
    @Named("mainRetrofit")
    fun provideRetrofitInstance(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicManagerApi(@Named("mainRetrofit") retrofit: Retrofit): MusicManagerApi {
        return retrofit.create(MusicManagerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMemberManagerApi(@Named("mainRetrofit") retrofit: Retrofit): MemberManagerApi {
        return retrofit.create(MemberManagerApi::class.java)
    }

}