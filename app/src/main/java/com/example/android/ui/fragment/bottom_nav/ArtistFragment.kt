package com.example.android.ui.fragment.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.R
import com.example.android.adapter.ArtistAdapter
import com.example.android.databinding.FragmentArtistBinding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Artist
import com.example.android.model.Song
import com.example.android.ui.fragment.BaseFragment
import com.example.android.viewmodel.ChannelViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ArtistFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val artistBinding: FragmentArtistBinding by lazy {
        FragmentArtistBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ChannelViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListArtists()

        return artistBinding.root
    }

    private fun displayListArtists() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        artistBinding.rcvData.layoutManager = layout
        val adapter = ArtistAdapter(object : IOnClickSongItemListener{
            override fun onClickItemArtist(artist: Artist) {
                val bundle = bundleOf(
                    "artist" to artist
                )
                findNavController().navigate(R.id.action_homepageFragment_to_arcticsFragment, bundle)
            }

            override fun onClickItemSong(song: Song) {
                TODO("Not yet implemented")
            }

        })
        artistBinding.rcvData.adapter = adapter

        viewModel.artistList.observe(viewLifecycleOwner) { artistList ->
            adapter.submitList(artistList)
        }

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}