package com.example.android.ui.fragment.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.adapter.SongAdapter
import com.example.android.databinding.FragmentPodcastBinding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Artist
import com.example.android.model.Song
import com.example.android.service.MusicService
import com.example.android.service.MusicServiceFunction
import com.example.android.ui.activity.PlayMusicActivity
import com.example.android.ui.fragment.BaseFragment
import com.example.android.viewmodel.ChannelViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class PodcastFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding: FragmentPodcastBinding by lazy {
        FragmentPodcastBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ChannelViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListPodcast()

        return binding.root
    }

    private fun displayListPodcast() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvData.layoutManager = layout
        val adapter = SongAdapter(object : IOnClickSongItemListener {
            override fun onClickItemArtist(artist: Artist) {
                TODO("Not yet implemented")
            }

            override fun onClickItemSong(song: Song) {
                goToSongDetail(song)
            }

        })
        binding.rcvData.adapter = adapter

        viewModel.getPodcastFragment()
        viewModel.podcastFragment.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun goToSongDetail(song: Song) {
        MusicService.clearListSongPlaying()
        MusicService.listSongPlaying?.add(song)
        MusicService.isPlaying = false
        MusicServiceFunction().startMusicService(requireContext(), MusicService.PLAY, 0)
        MusicServiceFunction().startActivity(requireContext(), PlayMusicActivity::class.java)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}