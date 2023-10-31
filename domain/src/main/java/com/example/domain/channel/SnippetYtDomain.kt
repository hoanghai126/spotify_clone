package com.example.domain.channel
data class SnippetYtDomain(
    val title: String? = null,
    val description: String? = null,
    val customUrl: String? = null,
    val publishedAt: String? = null,
    val thumbnails: ThumbnailsYtDomain? = null,
    val country: String? = null,
    val channelId: String? = null
)