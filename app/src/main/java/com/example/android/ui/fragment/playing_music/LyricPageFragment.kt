package com.example.android.ui.fragment.playing_music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.R
import com.example.android.databinding.FragmentArtistBinding
import com.example.android.databinding.FragmentLyricPageBinding

class LyricPageFragment : Fragment() {

    private val lyricBinding: FragmentLyricPageBinding by lazy {
        FragmentLyricPageBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return lyricBinding.root
    }
}