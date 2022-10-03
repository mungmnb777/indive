package com.ssafy.indive.repository

import com.ssafy.indive.api.MusicManagerApi
import com.ssafy.indive.datasource.MusicManagerDataSource
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.ReplyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicManagerRepository @Inject constructor(
    private val musicManagerDataSource: MusicManagerDataSource
) {

    fun getMusics(
        title: String?,
        artistName: String?,
        artistSeq: Long?,
        sort: String?,
        genre: String?,
        page: Int?,
        size: Int?
    ): Flow<Result<List<MusicDetailResponse>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.getMusics(title, artistName, artistSeq, sort, genre, page, size).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun addMusic(
        dto: Map<String, RequestBody>,
        image: MultipartBody.Part?,
        musicFile: MultipartBody.Part
    ): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.addMusic(dto, image, musicFile).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }


    fun getMusicDetails(musicSeq: Long): Flow<Result<Response<MusicDetailResponse>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.getMusicDetails(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun modifyMusic(musicSeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.modifyMusic(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun deleteMusic(musicSeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.deleteMusic(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun likeMusic(musicSeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.likeMusic(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun isLike(musicSeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.isLike(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun deleteLike(musicSeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.deleteLike(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun getLikeCount(musicSeq: Long): Flow<Result<Response<Int>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.getLikeCount(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun addMusicReply(musicSeq: Long,content : String): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.addMusicReply(musicSeq,content).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun getMusicReply(musicSeq: Long): Flow<Result<List<ReplyResponse>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.getMusicReply(musicSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun modifyMusicReply(musicSeq: Long, content: String, replySeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.modifyMusicReply(musicSeq, content,replySeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun deleteMusicReply(musicSeq: Long, replySeq: Long): Flow<Result<Response<Boolean>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.deleteMusicReply(musicSeq,replySeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e -> emit(Result.Error(e)) }

    fun getMyMusics(): Flow<Result<List<MusicDetailResponse>>> = flow {
        emit(Result.Loading)
        musicManagerDataSource.getMyMusics().collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}