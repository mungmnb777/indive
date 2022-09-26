package com.ssafy.indive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.entity.PlayListEntity
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.repository.PlayListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

@HiltViewModel
class MainViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {

    private val _playList = MutableLiveData<List<PlayListEntity>>()
    val playList: LiveData<List<PlayListEntity>>
        get() = _playList

    private val _musicDetails: MutableStateFlow<Result<Response<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val musicDetails get() = _musicDetails.asStateFlow()


    fun insert(musicSeq: Long) {


        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusicDetails(musicSeq).collectLatest {
                if (it is Result.Success) {
                    _musicDetails.value = it
                }

            }


            val song1 = PlayListEntity(
                0,
                "제목",
                "https://ncsmusic.s3.eu-west-1.amazonaws.com/tracks/000/001/186/would-you-be-waiting-1661508036-zZP2aUjM3n.mp3",
                "가수",
                "https://ncsmusic.s3.eu-west-1.amazonaws.com/tracks/000/001/186/100x100/1661851996_RswjEVwZaX_final.jpg"
            )
            val song2 = PlayListEntity(
                0,
                "제목3",
                "https://ncsmusic.s3.eu-west-1.amazonaws.com/tracks/000/001/182/caramel-1660903238-cWq7jpyG3h.mp3",
                "가수3",
                "https://ncsmusic.s3.eu-west-1.amazonaws.com/tracks/000/001/182/100x100/caramel-1660903236-noLU7SrAfr.jpg"
            )

            playListRepository.insertPlayList(song1)
            playListRepository.insertPlayList(song2)
            getAll()
        }

    }

    fun getAll() {

        viewModelScope.launch(Dispatchers.IO) {
            val res = playListRepository.getAllPlayList()

            _playList.postValue(res)
        }

    }
}