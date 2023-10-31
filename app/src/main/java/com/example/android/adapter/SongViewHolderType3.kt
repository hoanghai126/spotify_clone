package com.example.android.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.databinding.ItemSongType3Binding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Song

class SongViewHolderType3(
    private val binding: ItemSongType3Binding,
    private val clickListener: IOnClickSongItemListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(song: Song) {
        binding.tvTodayHitName.text = song.nameSong
        binding.tvTodayHitArtist.text = song.artistSongName
        Glide.with(binding.root)
            .load(song.imageSong)
            .error(R.drawable.image_not_available)
            .into(binding.imgSong)
        binding.itemLayout.setOnClickListener {
            clickListener.onClickItemSong(song)
        }
    }
}