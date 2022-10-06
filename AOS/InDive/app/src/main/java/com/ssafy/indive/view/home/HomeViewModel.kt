package com.ssafy.indive.view.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MemberManagerRepository
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memberManagerRepository: MemberManagerRepository,
    private val musicManagerRepository: MusicManagerRepository,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    private val _musicList: MutableStateFlow<Result<List<Music>>> =
        MutableStateFlow(Result.Unintialized)
    val musicList get() = _musicList.asStateFlow()

    private val _str: MutableStateFlow<String> = MutableStateFlow("")
    val str get() = _str.asStateFlow()

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    private val _latestMusicList: MutableStateFlow<Result<List<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val latestMusicList get() = _latestMusicList

    private val _popularMusicList: MutableStateFlow<Result<List<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val popularMusicList get() = _popularMusicList


    fun getMusics(title: String?, artistName: String?, artistSeq: Long?, sort: String?, genre: String?, page : Int?, size : Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusics(title, artistName, artistSeq, sort, genre,page, size).collectLatest {

                Log.d(TAG, "getMusics: ")
                if (it is Result.Success) {
                    when (sort) {
                        "popular" -> {
                            _popularMusicList.value = it
                        }
                        "latest" -> {
                            _latestMusicList.value = it
                        }
                    }

                } else if (it is Result.Error) {
                    Log.d(TAG, "Error: ${it.exception}")
                }


            }
        }
    }

    fun savePrivateKey(privateKey: String){
        val email = sharedPref.getString(USER_EMAIL, "")!!
        val credentials = Credentials.create(privateKey)

        // base64 + RSA 로 암호화한 Private Key 저장
        val encryptedPrivateKey = encrypt(privateKey)
        Log.d(TAG, "EncryptedPrivateKey: $encryptedPrivateKey")
        sharedPref.edit().putString(email, encryptedPrivateKey).apply()
    }
}