package com.example.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.model.Playlist

class PlaylistAdapter (private val item: List<Playlist>) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPlaylist: ImageView = itemView.findViewById(R.id.imgPlaylist)
        val tvPlaylistName: TextView = itemView.findViewById(R.id.tvPlaylistName)
        val tvNumberOfSongs: TextView = itemView.findViewById(R.id.tvNumberOfSongs)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = item[position]
        holder.imgPlaylist.setImageResource(currentItem.imgPlaylist)
        holder.tvPlaylistName.text = currentItem.playlistName
        holder.tvNumberOfSongs.text = currentItem.numberOfSongs.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return ViewHolder(itemView)
    }
}