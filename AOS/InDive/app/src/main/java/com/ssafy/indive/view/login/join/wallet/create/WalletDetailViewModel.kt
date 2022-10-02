package com.ssafy.indive.view.login.join.wallet.create

import android.content.SharedPreferences
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
    private val admin: Admin,
    private val sharedPref: SharedPreferences
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
    fun createWallet(password: String, email: String){
        val ecKeyPair = Keys.createEcKeyPair()
        val privateKeyInDec = ecKeyPair.privateKey

        _privateKey.value = privateKeyInDec.toString(16)

        val aWallet = Wallet.createLight(password, ecKeyPair)
        _address.value = "0x" + aWallet.address
        Log.d(TAG, "createWallet: ${_address.value}")

        admin.unlockAccount(ADMIN_ADDRESS, ADMIN_PASSWORD)

        // 생성된 주소로 1이더 전송
        web3j.sendTransaction(ADMIN_ADDRESS, _address.value, BigInteger("1000000000000000000"), "init")
        _transactionSuccess.postValue("success")

        admin.unlockAccount(ADMIN_ADDRESS, ADMIN_PASSWORD)
        // Admin 에서 사용자에게 토큰 1000개 출금 허용
        web3j.setTokenApprove(ADMIN_PRIVATE_KEY, INDIVE_ADDRESS, 1000)
        // 1000개 전송
        web3j.donate(ADMIN_PRIVATE_KEY, _address.value, 1000, "adminToUser")
        web3j.getTokenBalanceOf(_privateKey.value, _address.value)

        // base64 + RSA 로 암호화한 Private Key 저장
        val encryptedPrivateKey = encrypt(_privateKey.value)
        Log.d(TAG, "EncryptedPrivateKey: $encryptedPrivateKey")

        sharedPref.edit().putString(email, encryptedPrivateKey).apply()
    }
}