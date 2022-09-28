package com.ssafy.indive.view.genre.genrelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MusicManagerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class GenreListViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
): ViewModel() {

    private val _genreList : MutableStateFlow<Result<List<MusicDetailResponse>>> = MutableStateFlow(Result.Unintialized)
    val genreList get() = _genreList.asStateFlow()

    fun getGenreList(genre: String){
        viewModelScope.launch (Dispatchers.IO){
            musicManagerRepository.getMusics(null, null, null, genre,null,null).collectLatest {
                Log.d(TAG, "getGenreList: init")
                if (it is Result.Success) {
                    Log.d(TAG, "getGenreList: ${it.data}")
                    _genreList.value = it
                } else if (it is Result.Error) {
                    Log.d(TAG, "getGenreList: $it")
                }

            }
        }
    }


}