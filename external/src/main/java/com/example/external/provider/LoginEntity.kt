package com.example.external.provider

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)