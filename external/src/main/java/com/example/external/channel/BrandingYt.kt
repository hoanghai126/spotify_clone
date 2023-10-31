package com.example.external.channel

import com.example.domain.channel.BrandingYtDomain
import com.google.gson.annotations.SerializedName

data class BrandingYt(
    @SerializedName("image")
    val image: ImageBanner
) {

    data class ImageBanner(
        @SerializedName("bannerExternalUrl")
        val bannerUrl: String
    )

    fun mapBrandingYtToDomain(branding: BrandingYt): BrandingYtDomain {
        return BrandingYtDomain(
            image = branding.image.let { image ->
                BrandingYtDomain.ImageBanner(
                    bannerUrl = image.bannerUrl
                )
            }
        )
    }
}