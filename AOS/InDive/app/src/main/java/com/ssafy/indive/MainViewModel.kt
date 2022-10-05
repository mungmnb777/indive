package com.ssafy.indive
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.entity.PlayListEntity
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MemberManagerRepository
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.repository.PlayListRepository
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
class MainViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {

    private val _playList: MutableStateFlow<List<PlayListEntity>> = MutableStateFlow(listOf())
    val playList get() = _playList.asStateFlow()

    private val _musicDetails: MutableStateFlow<Result<Response<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val musicDetails get() = _musicDetails.asStateFlow()

    var successGetEvent = 0L


    fun insert(musicSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusicDetails(musicSeq).collectLatest {
                if (it is Result.Success) {
                    _musicDetails.value = it
                    val musicDetail = it.data.body()!!
                    val memberSeq = musicDetail.artist.memberSeq
                    val memberAddress = musicDetail.artist.wallet
                    val title = musicDetail.title
                    val artist = musicDetail.artist.nickname
                    val song = PlayListEntity(
                        0,
                        memberSeq,
                        memberAddress,
                        musicSeq,
                        title,
                        "$MUSIC_HEADER$musicSeq$MUSIC_FOOTER",
                        artist,
                        "$COVER_HEADER$musicSeq$COVER_FOOTER"
                    )

                    playListRepository.insertPlayList(song)
                } else if (it is Result.Error) {
                    Log.d(TAG, "Error: ${it.exception}")

                }
            }

        }


    }


    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            playListRepository.getAllPlayList().collectLatest {
                if (it is Result.Success) {
                    _playList.value = it.data
                    Log.d("MainViewModel", "getAll: ${it.data}")
                } else if (it is Result.Empty) {
                    _playList.value = listOf()
                } else if (it is Result.Error) {
                    Log.d("MainViewModel", "getAllError: ${it}")
                }

            }

        }

    }
}