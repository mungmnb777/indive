package com.ssafy.indive.view.rewardstore.addreward

import androidx.lifecycle.ViewModel
import com.ssafy.indive.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddRewardViewModel @Inject constructor(

) : ViewModel() {

    private val _quantitiy : MutableStateFlow<Int> = MutableStateFlow(0)
    val quantity get() = _quantitiy.asStateFlow()

    private val _cost : MutableStateFlow<Int> = MutableStateFlow(0)
    val cost get() = _cost.asStateFlow()

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    fun createNFT(img : MultipartBody.Part){
        _successMsgEvent.postValue("NFT 등록에 성공했습니다.")
    }

}