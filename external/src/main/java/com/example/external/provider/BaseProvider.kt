package com.example.external.provider

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import  com.example.domain.result.Result

open class BaseProvider constructor(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T>? {
        return withContext(coroutineDispatcher) {
            try {
                Result.Success(apiCall.invoke())
            } catch (e: Throwable) {
                e.printStackTrace()
                e.message?.let { Log.d("Exception ", it) }
                Result.Error(e)
            }
        }
    }
}