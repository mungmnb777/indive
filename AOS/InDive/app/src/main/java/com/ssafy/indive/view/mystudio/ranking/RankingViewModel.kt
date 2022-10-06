package com.ssafy.indive.view.mystudio.ranking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.response.DonationRankResponse
import com.ssafy.indive.model.response.MemberDetailResponse
import com.ssafy.indive.repository.MemberManagerRepository
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.SingleLiveEvent
import com.ssafy.indive.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val memberManagerRepository: MemberManagerRepository
) : ViewModel() {

    private val _rankingList : MutableStateFlow<Result<List<DonationRankResponse>>> = MutableStateFlow(
        Result.Unintialized)
    val rankingList get() = _rankingList.asStateFlow()
    
    fun getRankingList(address: String){
        viewModelScope.launch(Dispatchers.IO) { 
            memberManagerRepository.donationRankingByAddress(address).collectLatest {
                Log.d(TAG, "getRankingList: init")
                if (it is Result.Success){
                    Log.d(TAG, "getRankingList: success : ${it.data}")
                    _rankingList.value = it
                }else if(it is Result.Error){
                    Log.d(TAG, "getRankingList: error : ${it}")
                }
            }
        }
    }
}