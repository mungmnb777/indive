package com.ssafy.indive.datasource

import com.ssafy.indive.api.MusicManagerApi
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.ReplyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicManagerDataSource @Inject constructor(
    private val musicManagerApi: MusicManagerApi
) {

    fun getMusics(
        title: String?,
        artistName: String?,
        sort: String?,
        genre: String?
    ): Flow<List<Music>> = flow {
        emit(musicManagerApi.getMusics(title, artistName, sort, genre))
    }

    fun getMusicDetails(musicSeq: Long): Flow<Response<MusicDetailResponse>> = flow {
        emit(musicManagerApi.getMusicDetails(musicSeq))
    }

    fun modifyMusic(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.modifyMusic(musicSeq))
    }

    fun deleteMusic(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.deleteMusic(musicSeq))
    }

    fun likeMusic(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.likeMusic(musicSeq))
    }

    fun isLike(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.isLike(musicSeq))
    }

    fun deleteLike(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.deleteLike(musicSeq))
    }


    fun getLikeCount(musicSeq: Long): Flow<Response<Int>> = flow {
        emit(musicManagerApi.getLikeCount(musicSeq))
    }

    fun addMusicReply(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.addMusicReply(musicSeq))
    }

    fun getMusicReply(musicSeq: Long): Flow<Response<List<ReplyResponse>>> = flow {
        emit(musicManagerApi.getMusicReply(musicSeq))
    }

    fun modifyMusicReply(musicSeq: Long, replySeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.modifyMusicReply(musicSeq, replySeq))
    }

    fun deleteMusicReply(musicSeq: Long): Flow<Response<Boolean>> = flow {
        emit(musicManagerApi.deleteMusicReply(musicSeq))
    }

}