package com.ssafy.indive.view.songdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.indive.R
import com.ssafy.indive.model.dto.Comment

class SongDetailViewModel : ViewModel() {

    private val _commentList = MutableLiveData<MutableList<Comment>>()
    val commentList: LiveData<MutableList<Comment>>
        get() = _commentList

    fun getComments() {

        val commentList = mutableListOf(
            Comment(0, 1, R.drawable.img_ive_cover, "닉네임 1", "1", "댓글 1"),
            Comment(1, 2, R.drawable.ic_launcher_background, "닉네임 2", "2", "댓글 2"),
            Comment(2, 3, R.drawable.ic_launcher_foreground, "닉네임 3", "3", "댓글 3"),
            Comment(3, 4, R.drawable.img_ive_cover, "닉네임 4", "4", "댓글 4"),
            Comment(4, 5, R.drawable.ic_launcher_background, "닉네임 5", "5", "댓글 5"),
            Comment(5, 6, R.drawable.ic_launcher_foreground, "닉네임 6", "6", "댓글 6"),
            Comment(6, 7, R.drawable.img_ive_cover, "닉네임 1", "7", "댓글 7"),
        )

        _commentList.postValue(commentList)
    }


}