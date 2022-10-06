package com.ssafy.indive.view.more.box

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.ssafy.indive.utils.*
import com.ssafy.indive.utils.USER_EMAIL
import com.ssafy.indive.utils.decrypt
import com.ssafy.indive.utils.getNFTTokens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import javax.inject.Inject

@HiltViewModel
class BoxViewModel @Inject constructor(
    private val web3j: Web3j,
    private val sharedPref: SharedPreferences
) : ViewModel(){

    private val _nftList : MutableStateFlow<Result<List<String>>> = MutableStateFlow(Result.Unintialized)
    val nftList get() = _nftList.asStateFlow()

    fun getNFTTokenList(){
        val email = sharedPref.getString(USER_EMAIL, "")
        val encryptedPrivateKey = sharedPref.getString(email, "")

        val privateKey = decrypt(encryptedPrivateKey!!)
        val credential = Credentials.create(privateKey)

        val nftTokenList = web3j.getNFTTokens(privateKey, credential.address)

        _nftList.value = Result.Success(nftTokenList)
    }

}