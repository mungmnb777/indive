package com.ssafy.indive.view.userstudio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserStudioViewModel"
@HiltViewModel
class UserStudioViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
): ViewModel() {
    private val _userMusicList: MutableStateFlow<Result<List<MusicDetailResponse>>> = MutableStateFlow(
        Result.Unintialized)
    val userMusicList get() = _userMusicList.asStateFlow()

    fun getUserMusicList(artistSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusics(null, null, artistSeq, null, null, null, null).collectLatest {
                if(it is Result.Success){
                    _userMusicList.value = it
                } else if (it is Result.Error) {
                    Log.d(TAG, "UserStudioViewModel: $it")
                }
            }
        }
    }
}