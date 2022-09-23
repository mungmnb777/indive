package com.ssafy.indive.api

import com.ssafy.indive.model.dto.Music
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicManagerApi {

    @GET("music")
    suspend fun getMusics(@Query("title") title: String?, @Query("artist") artistName : String?,
        @Query("sort") sort: String?, @Query("genre") genre: String?) : List<Music>


}