package com.example.android.model

import java.io.Serializable

data class Song(
    val viewType: Int,
    val imageSong: String,
    val nameSong: String,
    val artistSongName: String,
    val url: String
) : Serializable
