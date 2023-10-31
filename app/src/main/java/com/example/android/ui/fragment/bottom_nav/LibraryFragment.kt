package com.example.android.ui.fragment.bottom_nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.R
import com.example.android.adapter.PlaylistAdapter
import com.example.android.databinding.FragmentLibraryBinding
import com.example.android.model.Playlist

class LibraryFragment : Fragment() {

    private val libraryBinding: FragmentLibraryBinding by lazy {
        FragmentLibraryBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListPlaylists()

        return libraryBinding.root
    }

    private fun displayListPlaylists() {
        val layout = GridLayoutManager(requireContext(),2)
        libraryBinding.rcvData.layoutManager = layout
        val adapter = PlaylistAdapter(getListPlaylists())
        libraryBinding.rcvData.adapter = adapter
    }

    private fun getListPlaylists(): List<Playlist> {
        val list = mutableListOf<Playlist>()

        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))
        list.add(Playlist(R.drawable.img_song_test, "Liked Songs",128))

        return list
    }
}