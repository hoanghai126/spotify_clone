package com.example.android.model

import java.io.Serializable

data class Artist(
    val imageArtist: String,
    val nameArtist: String,
    val descriptionArtist : String,
    val channelId : String
) : Serializable
