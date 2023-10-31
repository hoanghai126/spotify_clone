package com.example.external.channel

import com.example.domain.channel.ThumbnailsYtDomain
import com.google.gson.annotations.SerializedName

data class ThumbnailsYt(
    @SerializedName("medium")
    val medium: Medium
) {
    data class Medium(
        @SerializedName("url")
        val url: String
    )

    fun mapThumbnailsYtToDomain(thumbnailsYt: ThumbnailsYt): ThumbnailsYtDomain {
        return ThumbnailsYtDomain(
            medium = thumbnailsYt.medium.let { medium ->
                ThumbnailsYtDomain.Medium(
                    url = medium.url
                )
            }
        )
    }

}