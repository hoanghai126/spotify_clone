package com.example.android.listeners

import com.example.android.model.Artist
import com.example.android.model.Song

interface IOnClickSongItemListener {
    fun onClickItemArtist(artist: Artist)
    fun onClickItemSong(song: Song)
}