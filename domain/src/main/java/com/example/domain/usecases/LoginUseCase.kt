package com.example.domain.usecases

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.domain.result.Result
class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Flow<Result<LoginResponseDomain>> =
        loginRepository.login()
}