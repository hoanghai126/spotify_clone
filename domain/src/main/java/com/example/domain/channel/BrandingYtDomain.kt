package com.example.domain.channel

data class BrandingYtDomain(
    val image: ImageBanner? = null
) {
    data class ImageBanner(
        val bannerUrl: String? = null
    )
}