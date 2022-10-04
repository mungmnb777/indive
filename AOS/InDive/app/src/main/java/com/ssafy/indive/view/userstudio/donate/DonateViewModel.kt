package com.ssafy.indive.view.userstudio.donate

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.api.NFTApi
import com.ssafy.indive.model.dto.NFTAmount
import com.ssafy.indive.repository.MemberManagerRepository
import com.ssafy.indive.repository.NFTRepository
import com.ssafy.indive.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow
import org.web3j.protocol.Web3j

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val nftRepository: NFTRepository,
    private val web3j: Web3j,
    private val sharedPreferences: SharedPreferences,
    private val memberManagerRepository: MemberManagerRepository
) : ViewModel() {

    val quantity: MutableStateFlow<String> = MutableStateFlow("0")

    val memo: MutableStateFlow<String> = MutableStateFlow("")

    private val _successMsgEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val successMsgEvent get() = _successMsgEvent

    private val _priceToGetNFT: MutableStateFlow<Int> = MutableStateFlow(0)
    val priceToGetNFT get() = _priceToGetNFT.asStateFlow()

    private val _artistAddress : MutableStateFlow<String> = MutableStateFlow("")
    val artistAddress get() = _artistAddress.asStateFlow()

    fun putRewardNFT(artistSeq: Long) {
        if(priceToGetNFT.value <= quantity.value.toInt()) {

            viewModelScope.launch(Dispatchers.IO) {
                nftRepository.putRewardNFT(NFTAmount(quantity.value.toInt(), artistSeq))
                    .collectLatest {
                        if (it is Result.Success) {
                            if (it.data) {
                                _successMsgEvent.postValue("NFT를 수령하셨습니다.")
                            }

                        } else if (it is Result.Error) {
                            Log.d(TAG, "putRewardNFT: $it")
                        }
                    }
            }
        }
    }

    fun donate(){
        val email = sharedPreferences.getString(USER_EMAIL, "")
        val encryptedPrivateKey = sharedPreferences.getString(email, "")
        val decryptePrivateKey = decrypt(encryptedPrivateKey!!)

        web3j.donate(decryptePrivateKey, artistAddress.value, quantity.value.toInt(), memo.value)
        _successMsgEvent.postValue("후원 완료했습니다.")
    }

    fun checkIsGetNFT(artistSeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            nftRepository.checkIsGetNFT(artistSeq).collectLatest {
                if (it is Result.Success) {
                    _priceToGetNFT.value = it.data.amount
                    Log.d(TAG, "checkIsGetNFT: ${it.data.amount} ")
                }
            }
        }
    }


    fun memberDetail(memberSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            memberManagerRepository.memberDetail(memberSeq).collectLatest {
                if (it is Result.Success) {
                    _artistAddress.value = it.data.body()!!.wallet
                }
            }
        }
    }

}