package com.example.external.provider

import retrofit2.http.GET

interface LoginService {
    @GET("v3/e2ac203c-87fb-4b01-8059-6105eb562da3")
    suspend fun login(): LoginEntity
}