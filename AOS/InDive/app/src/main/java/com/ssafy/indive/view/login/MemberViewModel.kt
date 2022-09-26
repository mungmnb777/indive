package com.ssafy.indive.view.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.dto.MemberJoin
import com.ssafy.indive.model.dto.MemberLogin
import com.ssafy.indive.repository.MemberManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject
import com.ssafy.indive.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val TAG = "MemberViewModel"
@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberManagerRepository: MemberManagerRepository
): ViewModel() {
    private val _login: MutableStateFlow<Result<Response<String>>> =
        MutableStateFlow(Result.Unintialized)
    val login get() = _login.asStateFlow()

    private val _join: MutableStateFlow<Result<Response<Boolean>>> =
        MutableStateFlow(Result.Unintialized)
    val join get() = _join.asStateFlow()

    fun memberLogin(memberLogin: MemberLogin){
        viewModelScope.launch(Dispatchers.IO) {
            memberManagerRepository.login(memberLogin).collectLatest {
                _login.value = it
                Log.d(TAG, "memberLogin: ${it}")
                if(it is Result.Success){
                    Log.d(TAG, "memberLogin: ${it.data}")
                }
            }
        }
    }

    fun memberJoin(memberJoin: MemberJoin) {
        viewModelScope.launch(Dispatchers.IO) {
            memberManagerRepository.join(memberJoin).collectLatest {
                _join.value = it
                Log.d(TAG, "memberJoin: ${it}")
                if(it is Result.Success){
                    Log.d(TAG, "memberJoin: ${it.data.body()}")
                    Log.d(TAG, "memberJoin: ${it.data.headers()["Authorization"]}")
            } }
        }
    }

}