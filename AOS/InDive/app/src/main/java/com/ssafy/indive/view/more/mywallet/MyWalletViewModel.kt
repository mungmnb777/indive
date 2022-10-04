package com.ssafy.indive.view.more.mywallet

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.ssafy.indive.utils.SingleLiveEvent
import com.ssafy.indive.utils.USER_EMAIL
import com.ssafy.indive.utils.decrypt
import com.ssafy.indive.utils.getTokenBalanceOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import javax.inject.Inject

class MyWalletViewModel @Inject constructor(
    private val web3j: Web3j,
    private val admin: Admin,
    private val sharedPref: SharedPreferences
) : ViewModel() {
    private val _balance: MutableStateFlow<Int> =
        MutableStateFlow(0)
    val balance get() = _balance.asStateFlow()

    private val _tokenSuccess = SingleLiveEvent<String>()
    val tokenSuccess get() = _tokenSuccess

    fun getTokenBalanceOf(){
        val email = sharedPref.getString(USER_EMAIL, "")
        val encryptedPrivateKey = sharedPref.getString(email, "")

        val privateKey = decrypt(encryptedPrivateKey!!)
        val credential = Credentials.create(privateKey)

        val balance = web3j.getTokenBalanceOf(privateKey, credential.address)

        _tokenSuccess.postValue("success")
        _balance.value = balance
    }

    fun getDonationHistory(){
        val email = sharedPref.getString(USER_EMAIL, "")
        val encryptedPrivateKey = sharedPref.getString(email, "")
    }
}