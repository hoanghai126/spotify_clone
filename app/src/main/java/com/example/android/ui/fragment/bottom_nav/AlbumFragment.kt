package com.example.android.ui.fragment.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.R
import com.example.android.adapter.AlbumAdapter
import com.example.android.adapter.ArtistAdapter
import com.example.android.databinding.FragmentAlbumBinding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Artist
import com.example.android.model.Song
import com.example.android.ui.fragment.BaseFragment
import com.example.android.viewmodel.ChannelViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AlbumFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding: FragmentAlbumBinding by lazy {
        FragmentAlbumBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ChannelViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListAlbum()

        return binding.root
    }

    private fun displayListAlbum() {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.rcvData.layoutManager = layout
        val adapter = AlbumAdapter()
//        val adapter = ArtistAdapter(object : IOnClickSongItemListener{
//            override fun onClickItemArtist(artist: Artist) {
//                val bundle = bundleOf(
//                    "artist" to artist
//                )
//                findNavController().navigate(R.id.action_homepageFragment_to_arcticsFragment, bundle)
//            }
//
//            override fun onClickItemSong(song: Song) {
//                TODO("Not yet implemented")
//            }
//
//        })
        binding.rcvData.adapter = adapter

//        viewModel.getAlbumFragment()
        viewModel.albumFragment.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}