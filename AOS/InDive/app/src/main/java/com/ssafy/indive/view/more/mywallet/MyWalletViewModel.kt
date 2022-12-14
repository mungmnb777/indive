package com.ssafy.indive.view.more.mywallet

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.ssafy.indive.blockchain.DonationHistory
import com.ssafy.indive.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import javax.inject.Inject

@HiltViewModel
class MyWalletViewModel @Inject constructor(
    private val web3j: Web3j,
    private val sharedPref: SharedPreferences
) : ViewModel() {
    private val _balance: MutableStateFlow<Int> =
        MutableStateFlow(0)
    val balance get() = _balance.asStateFlow()

    private val _donationHistory: MutableStateFlow<Result<List<DonationHistory>>> =
        MutableStateFlow(Result.Unintialized)
    val donationHistory get() = _donationHistory.asStateFlow()

    fun getTokenBalanceOf(){
        val email = sharedPref.getString(USER_EMAIL, "")
        val encryptedPrivateKey = sharedPref.getString(email, "")

        val privateKey = decrypt(encryptedPrivateKey!!)
        val credential = Credentials.create(privateKey)

        val balance = web3j.getTokenBalanceOf(privateKey, credential.address)

        _balance.value = balance
    }

    fun getHistory(){
        val email = sharedPref.getString(USER_EMAIL, "")
        val encryptedPrivateKey = sharedPref.getString(email, "")

        val privateKey = decrypt(encryptedPrivateKey!!)
        val credential = Credentials.create(privateKey)

        _donationHistory.value = Result.Success((web3j.getDonationHistoryList(privateKey, credential.address)))
    }
}