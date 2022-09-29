package com.ssafy.indive.view.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.response.ReplyResponse
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.SingleLiveEvent
import com.ssafy.indive.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

const val TAG1 = "PlayerPlayer_"
@HiltViewModel
class PlayerViewModel @Inject constructor(private val musicManagerRepository: MusicManagerRepository) :
    ViewModel() {

    private val _likeSuccess = MutableStateFlow(false)
    val likeSuccess get() = _likeSuccess

    private val _deleteLikeSuccess = MutableStateFlow(false)
    val deleteLikeSuccess get() = _deleteLikeSuccess

    private val _replyListCnt = MutableStateFlow("")
    val replyListCnt get() = _replyListCnt.asStateFlow()

    private val _isLike = SingleLiveEvent<Boolean>()
    val isLike get() = _isLike

    var likeFlag = false

    private val _likeCnt = MutableStateFlow("0")
    val likeCnt get() = _likeCnt.asStateFlow()


    fun likeMusic(musicSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.likeMusic(musicSeq).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG1, "PlayerViewModel likeMusic: ${it.data.body()!!}")
                    _likeSuccess.value = it.data.body()!!
                    _isLike.postValue(true)
                    likeFlag = true
                } else if (it is Result.Error) {
                    Log.d(TAG1, "PlayerViewModel likeMusicErr: ${it.exception}")
                }
            }

        }
    }

    fun deleteLike(musicSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.deleteLike(musicSeq).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG1, "PlayerViewModel deleteLike: ${it.data.body()!!}")
                    _deleteLikeSuccess.value = it.data.body()!!
                    _isLike.postValue(false)
                    likeFlag = false
                } else if (it is Result.Error) {
                    Log.d(TAG1, "PlayerViewModel deleteLikeErr: ${it.exception}")
                }
            }

        }
    }


    fun getMusicReply(musicSeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusicReply(musicSeq).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG1, "PlayerViewModel:getMusicReply ${it.data.size}")
                    _replyListCnt.value = it.data.size.toString()

                } else if (it is Result.Error) {
                    Log.d(TAG1, "PlayerViewModel:getMusicReplyError ${it.exception}")
                }
            }

        }
    }

    fun isLike(musicSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.isLike(musicSeq).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG1, "PlayerViewModel isLike: ${it.data.body()!!}")
                    _isLike.postValue(it.data.body()!!)
                } else if (it is Result.Error) {
                    Log.d(TAG1, "PlayerViewModel isLikeError : ${it.exception}")
                }
            }

        }
    }

    fun getLikeCnt(musicSeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getLikeCount(musicSeq).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG1, "PlayerViewModel likeCnt: ${it.data.body()!!}")
                    _likeCnt.value = it.data.body()!!.toString()
                } else if (it is Result.Error) {
                    Log.d(TAG1, "PlayerViewModel likeCntError: ${it.exception}")
                }
            }

        }
    }

}