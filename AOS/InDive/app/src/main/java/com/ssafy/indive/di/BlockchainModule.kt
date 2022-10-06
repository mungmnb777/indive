package com.ssafy.indive.di

import com.ssafy.indive.utils.BLOCKCHAIN_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.http.HttpService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlockchainModule {

    @Provides
    @Singleton
    fun provideWeb3j(): Web3j{
        return Web3j.build(HttpService(BLOCKCHAIN_URL))
    }

    @Provides
    @Singleton
    fun provideAdmin(): Admin{
        return Admin.build(HttpService(BLOCKCHAIN_URL))
    }
}