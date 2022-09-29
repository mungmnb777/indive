package com.ssafy.indive.view.login.join.wallet.create

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ssafy.indive.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import org.web3j.protocol.Web3j
import org.web3j.protocol.admin.Admin
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class WalletDetailViewModel @Inject constructor(
    private val web3j: Web3j,
    private val admin: Admin
) : ViewModel() {
    private val _privateKey: MutableStateFlow<String> =
        MutableStateFlow("")
    val privateKey get() = _privateKey.asStateFlow()

    private val _address: MutableStateFlow<String> =
        MutableStateFlow("")
    val address get() = _address.asStateFlow()

    private val _transactionSuccess = SingleLiveEvent<String>()
    val transactionSuccess get() = _transactionSuccess

    // 지갑 생성
    fun createWallet(password: String){
        val ecKeyPair = Keys.createEcKeyPair()
        val privateKeyInDec = ecKeyPair.privateKey

        _privateKey.value = privateKeyInDec.toString(16)

        val aWallet = Wallet.createLight(password, ecKeyPair)
        _address.value = "0x" + aWallet.address
        Log.d(TAG, "createWallet: ${_address.value}")

        admin.unlockAccount(ADMIN_ADDRESS)

        // 생성된 주소로 1이더 전송
        web3j.sendTransaction(ADMIN_ADDRESS, _address.value, BigInteger.valueOf(100000000000000), "init")

        _transactionSuccess.postValue("success")
    }
}