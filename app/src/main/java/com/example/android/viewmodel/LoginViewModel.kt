package com.example.android.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.LoginUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.domain.result.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val mutableIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    private val mutableLoginResult = MutableLiveData<Boolean>()
    val logInResult: LiveData<Boolean>
        get() = mutableLoginResult

    private fun startLoading() {
        mutableIsLoading.postValue(true)
    }

    private fun stopLoading() {
        mutableIsLoading.postValue(false)
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginUseCase.invoke()
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            Log.d("#PhucNK1 ", it.data.toString())
                            mutableLoginResult.value = username == it.data.username && password == it.data.password
                        }
                        else -> mutableLoginResult.value = false
                    }
                }
        }
    }
}