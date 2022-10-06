package com.ssafy.indive.view.songdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.SingleLiveEvent
import com.ssafy.indive.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplyViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {


}