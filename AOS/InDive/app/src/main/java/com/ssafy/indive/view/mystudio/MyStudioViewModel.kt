package com.ssafy.indive.view.mystudio

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

private const val TAG = "MyStudioViewModel"
@HiltViewModel
class MyStudioViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
): ViewModel() {
    private val _myMusicList : MutableStateFlow<Result<List<MusicDetailResponse>>> = MutableStateFlow(
        Result.Unintialized)
    val myMusicList get() = _myMusicList.asStateFlow()

    fun getMusicList(){
        viewModelScope.launch (Dispatchers.IO){
            musicManagerRepository.getMyMusics().collectLatest {
                Log.d(TAG, "MyStudioViewModel1: init")
                if (it is Result.Success) {
                    Log.d(TAG, "MyStudioViewModel2: ${it.data}")
                    _myMusicList.value = it
                } else if (it is Result.Error) {
                    Log.d(TAG, "MyStudioViewModel3: $it")
                }
            }
        }
    }
}