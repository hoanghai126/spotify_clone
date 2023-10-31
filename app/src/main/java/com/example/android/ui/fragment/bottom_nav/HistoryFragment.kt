package com.example.android.ui.fragment.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.databinding.FragmentHistoryBinding
import com.example.android.ui.fragment.BaseFragment
import com.example.android.viewmodel.ChannelViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class HistoryFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var isExpanded = false

    private val binding: FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ChannelViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListSongToday()
        displayListSongYesterday()


        return binding.root
    }

    private fun displayListSongToday() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvToday.layoutManager = layout
//        val adapter = SongAdapter()
//        binding.rcvToday.adapter = adapter

//        adapter.submitList(getSong().subList(0, 2))
//
//        binding.showTvToday.setOnClickListener {
//            if (isExpanded) {
//                adapter.submitList(getSong().subList(0, 2))
//                binding.showTvToday.text = "See all ${getSong().size} played"
//
//            } else {
//                adapter.submitList(getSong())
//                binding.showTvToday.text = "Hide the playlist"
//            }
//            isExpanded = !isExpanded
//        }

    }

    private fun displayListSongYesterday() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvYesterday.layoutManager = layout
//        val adapter = SongAdapter()
//        binding.rcvYesterday.adapter = adapter

//        adapter.submitList(getSong().subList(0, 2))
//
//        binding.showTvYesterday.setOnClickListener {
//            if (isExpanded) {
//                adapter.submitList(getSong().subList(0, 2))
//                binding.showTvYesterday.text = "See all ${getSong().size} played"
//
//            } else {
//                adapter.submitList(getSong())
//                binding.showTvYesterday.text = "Hide the playlist"
//            }
//            isExpanded = !isExpanded
//        }

    }

//    private fun getSong(): List<Song> {
//        val list = mutableListOf<Song>()
//
//        list.add(Song(1, "SongABC", "Adele", "R.drawable.ran"))
//        list.add(Song(1, "SongABC", "Adele", "R.drawable.ran"))
//        list.add(Song(1, "SongABC", "Adele", "R.drawable.ran"))
//        list.add(Song(1, "SongABC", "Adele", "R.drawable.ran"))
//        list.add(Song(1, "SongABC", "Adele", "R.drawable.ran"))
//        list.add(Song(1, "SongABC", "Adele", "R.drawable.ran"))
//
//        return list
//    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}