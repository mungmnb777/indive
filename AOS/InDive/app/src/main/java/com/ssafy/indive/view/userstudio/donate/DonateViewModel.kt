package com.ssafy.indive.view.userstudio.donate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.api.NFTApi
import com.ssafy.indive.model.dto.NFTAmount
import com.ssafy.indive.repository.NFTRepository
import com.ssafy.indive.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.TAG
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val nftRepository: NFTRepository
) : ViewModel() {

    val quantity: MutableStateFlow<String> = MutableStateFlow("0")

    val memo: MutableStateFlow<String> = MutableStateFlow("")

    private val _successMsgEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val successMsgEvent get() = _successMsgEvent

    private val _priceToGetNFT: MutableStateFlow<Int> = MutableStateFlow(0)
    val priceToGetNFT get() = _priceToGetNFT.asStateFlow()

    fun putRewardNFT(artistSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            nftRepository.putRewardNFT(NFTAmount(quantity.value.toInt(), artistSeq)).collectLatest {
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


}