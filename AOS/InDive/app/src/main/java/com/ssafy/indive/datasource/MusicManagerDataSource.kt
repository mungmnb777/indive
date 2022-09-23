package com.ssafy.indive.datasource

import com.ssafy.indive.api.MusicManagerApi
import com.ssafy.indive.model.dto.Music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicManagerDataSource @Inject constructor(
    private val musicManagerApi: MusicManagerApi
){

    fun getMusics(title: String?, artistName: String?, sort: String?, genre: String?) : Flow<List<Music>> = flow {
        emit(musicManagerApi.getMusics(title, artistName, sort, genre))
    }



}