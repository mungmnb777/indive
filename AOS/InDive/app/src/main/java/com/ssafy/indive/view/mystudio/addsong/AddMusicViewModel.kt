package com.ssafy.indive.view.mystudio.addsong

import com.ssafy.indive.repository.MusicManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddMusicViewModel @Inject constructor(
    val musicManagerRepository: MusicManagerRepository
) {



}