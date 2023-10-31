package com.example.android.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.android.model.Artist

class MyDiffUtil(private val oldList: List<Artist>, private val newList: List<Artist>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].nameArtist == newList[newItemPosition].nameArtist
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].nameArtist == newList[newItemPosition].nameArtist -> {
                false
            }
            oldList[oldItemPosition].imageArtist == newList[newItemPosition].imageArtist -> {
                false
            }
            oldList[oldItemPosition].descriptionArtist == newList[newItemPosition].descriptionArtist -> {
                false
            }
            else -> true
        }
    }
}