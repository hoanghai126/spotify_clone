package com.example.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.databinding.ItemSongType1Binding
import com.example.android.databinding.ItemSongType2Binding
import com.example.android.databinding.ItemSongType3Binding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Song

class SongAdapter(private val clickListener: IOnClickSongItemListener) :
    ListAdapter<Song, RecyclerView.ViewHolder>(SongDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SongViewType.SONG_TYPE_1 -> {
                val binding = ItemSongType1Binding.inflate(inflater, parent, false)
                SongViewHolderType1(binding, clickListener)
            }
            SongViewType.SONG_TYPE_2 -> {
                val binding = ItemSongType2Binding.inflate(inflater, parent, false)
                SongViewHolderType2(binding, clickListener)
            }
            SongViewType.SONG_TYPE_3 -> {
                val binding = ItemSongType3Binding.inflate(inflater, parent, false)
                SongViewHolderType3(binding, clickListener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is SongViewHolderType1 -> {
                holder.bind(item)
            }
            is SongViewHolderType2 -> {
                holder.bind(item)
            }
            is SongViewHolderType3 -> {
                holder.bind(item)
            }
            else -> throw IllegalArgumentException("Invalid view holder type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    private class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.nameSong == newItem.nameSong
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }
}

