package com.example.domain.usecases

import kotlinx.coroutines.flow.Flow
import com.example.domain.result.Result
interface LoginRepository {
    suspend fun login(): Flow<Result<LoginResponseDomain>>
}
