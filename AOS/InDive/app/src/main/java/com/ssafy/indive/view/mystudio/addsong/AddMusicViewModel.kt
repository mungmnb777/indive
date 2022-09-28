package com.ssafy.indive.view.mystudio.addsong

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.indive.model.dto.AddMusic
import com.ssafy.indive.repository.MemberManagerRepository
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.SingleLiveEvent
import com.ssafy.indive.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddMusicViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository,
    private val memberManagerRepository: MemberManagerRepository
) : ViewModel() {

    val title: MutableStateFlow<String> = MutableStateFlow("")

    private var music: MultipartBody.Part? = null


    private var coverImg: MultipartBody.Part? = null

    val description: MutableStateFlow<String> = MutableStateFlow("")

    val genre: MutableStateFlow<String> = MutableStateFlow("장르선택 +")

    val composer: MutableStateFlow<String> = MutableStateFlow("")

    val lyricist: MutableStateFlow<String> = MutableStateFlow("")

    val lyrics: MutableStateFlow<String> = MutableStateFlow("")

    private val _startDate: MutableStateFlow<String> = MutableStateFlow("")
    val starDate get() = _startDate.asStateFlow()

    private val _startTime: MutableStateFlow<String> = MutableStateFlow("21:00")
    val startTime get() = _startTime.asStateFlow()

    private val _reservationDate: MutableStateFlow<String> = MutableStateFlow("")
    val reservationDate get() = _reservationDate.asStateFlow()

    private val _reservationTime: MutableStateFlow<String> = MutableStateFlow("21:00")
    val reservationTime get() = _reservationTime.asStateFlow()

    private val _successAddMusic = SingleLiveEvent<String>()
    val successAddMusic get() = _successAddMusic

    private val _failedAddMusic = SingleLiveEvent<String>()
    val failedAddMusic get() = _failedAddMusic

    fun refreshInit() {
        title.value = ""
        music = null
        coverImg = null
        description.value = ""
        genre.value = "장르선택 +"
        composer.value = ""
        lyricist.value = ""
        lyrics.value = ""
        _startDate.value = ""
        _startTime.value = ""
        _reservationDate.value = ""
        _reservationTime.value = ""

    }

    fun addMusic() {
        viewModelScope.launch(Dispatchers.IO) {
            val releaseDateTime = "${starDate.value} ${startTime.value}:00"
            val reservationDateTime = "${reservationDate.value} ${reservationTime.value}:00"

            Log.d(TAG, "releaseDateTime: $releaseDateTime")
            Log.d(TAG, "reservationDateTime: $reservationDateTime")

            val addTitle = RequestBody.create("text/plain".toMediaTypeOrNull(), title.value)
            val addlyricist = RequestBody.create("text/plain".toMediaTypeOrNull(), lyricist.value)
            val addcomposer = RequestBody.create("text/plain".toMediaTypeOrNull(), composer.value)
            val addgenre = RequestBody.create("text/plain".toMediaTypeOrNull(), genre.value)
            val adddescription =
                RequestBody.create("text/plain".toMediaTypeOrNull(), description.value)
            val addlyrics = RequestBody.create("text/plain".toMediaTypeOrNull(), lyrics.value)
            val addreleaseDateTime =
                RequestBody.create("text/plain".toMediaTypeOrNull(), releaseDateTime)
            val addreservationDateTime =
                RequestBody.create("text/plain".toMediaTypeOrNull(), reservationDateTime)

            val map = HashMap<String, RequestBody>()
            map["title"] = addTitle
            map["lyricist"] = addlyricist
            map["composer"] = addcomposer
            map["genre"] = addgenre
            map["description"] = adddescription
            map["lyrics"] = addlyrics
            map["releaseDateTime"] = addreleaseDateTime
            map["reservationDateTime"] = addreservationDateTime


//            val addMusic = AddMusic(
//                title.value,
//                lyricist.value,
//                composer.value,
//                genre.value,
//                description.value,
//                lyrics.value,
//                "2022-01-01 00:00:00",
//                "2022-01-01 00:00:00",
//                coverImg,
//                music
//            )
//
//            Log.d(TAG, "addMusic: $addMusic")
//            val json = Gson().toJson(addMusic)
//            val addMusicDTO =
//                RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
//            Log.d(TAG, "addMusicDTO: $addMusicDTO")
//
//            Log.d(TAG, "addMusicmusic: $music")

            Log.d(TAG, "addMusic Map: $map")
            Log.d(TAG, "addMusic coverImg: $coverImg")
            Log.d(TAG, "addMusic music: $music")

            musicManagerRepository.addMusic(map, coverImg, music!!).collectLatest {
                if (it is Result.Success) {
                    Log.d(TAG, "it.data : ${it.data}")
                    if (it.data) {
                        _successAddMusic.postValue("음원 등록 성공")
                    } else {
                        _failedAddMusic.postValue("음원 등록 실패")
                    }
                } else if (it is Result.Error) {
                    Log.d(TAG, "Error: ${it.exception}")
                    Log.d(TAG, "Error: ${it.exception.message}")
                    Log.d(TAG, "Error: ${it.exception.cause}")
                }
            }
        }
    }

    fun setMusic(music: MultipartBody.Part) {
        this.music = music
    }

    fun setCoverImg(coverImg: MultipartBody.Part?) {
        this.coverImg = coverImg
    }

    fun setGenre(genre: String) {
        this.genre.value = genre
    }

    fun setStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun setStartTime(startTime: String) {
        _startTime.value = startTime
    }

    fun setReservationDate(reservationDate: String) {
        _reservationDate.value = reservationDate
    }

    fun setReservationTime(reservationTime: String) {
        _reservationTime.value = reservationTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initDate() {
        val now = LocalDate.now().plusDays(1)
        val startDateYear = now.year
        val startDateMonthInt = now.monthValue
        var startDateMonth = startDateMonthInt.toString()
        if (startDateMonthInt < 10) {
            startDateMonth = "0" + startDateMonth
        }
        val startDateDayInt = now.dayOfMonth
        var startDateDay = startDateDayInt.toString()
        if (startDateDayInt < 10) {
            startDateDay = "0" + startDateDay
        }
        setStartDate("$startDateYear-${startDateMonth}-$startDateDay")
        setReservationDate("$startDateYear-${startDateMonth}-$startDateDay")
    }

}