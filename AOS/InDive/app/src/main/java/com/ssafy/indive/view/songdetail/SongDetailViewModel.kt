package com.ssafy.indive.view.songdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.R
import com.ssafy.indive.model.dto.Comment
import com.ssafy.indive.model.entity.PlayListEntity
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.ReplyResponse
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SongDetailViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {


    private val _musicDetails: MutableStateFlow<Result<Response<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val musicDetails get() = _musicDetails.asStateFlow()

    private var _musicDetail = SingleLiveEvent<MusicDetailResponse>()
    val musicDetail get() = _musicDetail

    private val _musicReplyList: MutableStateFlow<List<ReplyResponse>> = MutableStateFlow(listOf())
    val musicReplyList get() = _musicReplyList.asStateFlow()

    private val _replyListCnt = MutableStateFlow("")
    val replyListCnt get() = _replyListCnt.asStateFlow()

    private val _addReplySuccess = SingleLiveEvent<String>()
    val addReplySuccess get() = _addReplySuccess

    fun getMusicDetail(musicSeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusicDetails(musicSeq).collectLatest {
                if (it is Result.Success) {
                    _musicDetails.value = it

                    _musicDetail.postValue(it.data.body()!!)

                    Log.d(TAG, "getMusicDetail: ${it.data.body()!!}")

                } else if (it is Result.Error) {
                    Log.d(TAG, "Error: ${it.exception}")

                }
            }
        }
    }

    fun getMusicReply(musicSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            musicManagerRepository.getMusicReply(musicSeq).collectLatest {
                if (it is Result.Success) {
                    _musicReplyList.value = it.data
                    Log.d(TAG, "getMusicReply: ${it.data}")
                } else if (it is Result.Error) {
                    Log.d(TAG, "getMusicReplyError: ${it.exception}")
                }
            }

        }
    }

    fun addReply(musicSeq: Long, content: String) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.addMusicReply(musicSeq, content).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG, "addReply: $it")
                    if (it.data.body()!!) {
                        _musicReplyList.value = listOf()
                        _addReplySuccess.postValue("등록 성공")
                    }
                } else if (it is Result.Error) {
                    Log.d(TAG, "addReply: $it")
                }
            }


        }
    }
}