package com.example.external.channel

import com.example.domain.channel.IdYtDomain
import com.google.gson.annotations.SerializedName

data class IdYt(
    @SerializedName("videoId")
    val videoId: String
)
{
    fun mapIdYtToDomain(idYt: IdYt): IdYtDomain {
        return IdYtDomain(
            videoId = idYt.videoId
        )
    }

}