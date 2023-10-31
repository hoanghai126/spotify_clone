package com.example.external.provider

import com.example.domain.di.DefaultDispatcher
import com.example.domain.result.Result
import com.example.domain.usecases.LoginRepository
import com.example.domain.usecases.LoginResponseDomain
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LoginProvider @Inject constructor(
    private val loginService: LoginService,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : LoginRepository, BaseProvider(defaultDispatcher) {

    override suspend fun login(): Flow<Result<LoginResponseDomain>> {
        return flow {
            emit(safeApiCall {
                loginService.login()
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    Result.Success(
                        LoginResponseDomain(
                            username = it.data.username,
                            password = it.data.password
                        )
                    )

                }

                else -> Result.Success(LoginResponseDomain())
            }
        }
    }
}