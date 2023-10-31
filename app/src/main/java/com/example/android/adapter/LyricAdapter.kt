package com.example.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class LyricAdapter(private val lyricList: List<String>) :
    RecyclerView.Adapter<LyricAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lyric: TextView = itemView.findViewById(R.id.tv_lyric)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_text_lyric, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lyricList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLyric = lyricList[position]
        holder.lyric.text = currentLyric
    }
}
