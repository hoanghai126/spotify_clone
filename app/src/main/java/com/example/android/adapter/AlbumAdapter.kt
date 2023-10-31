package com.example.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.android.R
import com.example.android.databinding.ItemAlbumBinding
import com.example.android.model.Album

class AlbumAdapter :
    ListAdapter<Album, AlbumAdapter.AlbumHolder>(AlbumDiffCallback()) {

    inner class AlbumHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumName.text = album.albumName
            binding.yearRelease.text = album.yearReleased
            Glide.with(binding.root)
                .load(album.imgAlbum)
                .error(R.drawable.image_not_available)
                .into(binding.imgAlbum)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val binding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.albumName == newItem.albumName
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

}