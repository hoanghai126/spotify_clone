package com.example.domain.channel

data class ThumbnailsYtDomain(
    val medium: Medium? = null
) {
    data class Medium(
        val url: String? = null
    )
}