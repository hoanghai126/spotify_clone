package com.example.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.android.R
import com.example.android.databinding.ItemArtistBinding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Artist

class ArtistAdapter(private val clickListener: IOnClickSongItemListener) :
    ListAdapter<Artist, ArtistAdapter.ArtistHolder>(ArtistDiffCallback()) {
    inner class ArtistHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.tvArtistName.text = artist.nameArtist
            binding.tvArtistDescription.text = artist.descriptionArtist
            Glide.with(binding.root)
                .load(artist.imageArtist)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .error(R.drawable.image_not_available)
                .into(binding.imgArtist)
            binding.itemLayout.setOnClickListener {
                this@ArtistAdapter.clickListener.onClickItemArtist(artist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHolder {
        val binding =
            ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        val artist = getItem(position)
        holder.bind(artist)
    }

    class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.nameArtist == newItem.nameArtist
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }

}