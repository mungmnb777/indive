package com.ssafy.indive.view.mystudio.addreward

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.indive.model.dto.NFT
import com.ssafy.indive.repository.NFTRepository
import com.ssafy.indive.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MultipartBody
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddRewardViewModel @Inject constructor(
    private val nftRepository: NFTRepository
) : ViewModel() {

    val quantity: MutableStateFlow<String> = MutableStateFlow("0")

    val cost: MutableStateFlow<String> = MutableStateFlow("0")

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    fun addNFT(img: MultipartBody.Part) {
        val lowerDonationAmount = RequestBody.create("text/plain".toMediaTypeOrNull(), cost.value)
        val stock = RequestBody.create("text/plain".toMediaTypeOrNull(), quantity.value)

        Log.d(TAG, "addNFT: quantity : ${quantity.value}")
        Log.d(TAG, "addNFT: cost : ${cost.value}")
        
        val map = HashMap<String, RequestBody>()
        map["lowerDonationAmount"] = lowerDonationAmount
        map["stock"] = stock

        viewModelScope.launch(Dispatchers.IO) {
            nftRepository.addNFT(img, map).collectLatest {
                if (it is Result.Success) {
                    _successMsgEvent.postValue("NFT 등록에 성공했습니다.")
                } else if (it is Result.Error) {
                    Log.d(TAG, "addNFT: $it")
                }
            }
        }

    }

}