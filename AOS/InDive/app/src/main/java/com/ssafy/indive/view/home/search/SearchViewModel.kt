package com.ssafy.indive.view.home.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MusicManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    val musicManagerRepository: MusicManagerRepository
) : ViewModel() {

    private val _searchMusicList: MutableStateFlow<Result<List<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val searchMusicList get() = _searchMusicList.asStateFlow()


    private val _searchArtistList: MutableStateFlow<Result<List<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val searchArtistList get() = _searchArtistList.asStateFlow()

    fun getMusics(searchText: String) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusics(searchText, null, null, null, null, null)
                .collectLatest {
                    if (it is Result.Success) {
                        _searchMusicList.value = it
                    } else if (it is Result.Error) {
                        Log.d("SearchViewModel", "getMusicsListErr: $it")
                    }
                }

        }

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusics(null, searchText, null, null, null, null)
                .collectLatest {
                    if (it is Result.Success) {
                        _searchArtistList.value = it
                    } else if (it is Result.Error) {
                        Log.d("SearchViewModel", "getMusicsArtistErr: $it")
                    }
                }

        }

    }

}