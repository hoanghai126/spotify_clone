package com.example.domain.usecases

import java.io.Serializable

data class LoginResponseDomain(
    val username: String? = null,
    val password: String? = null
) : Serializable
