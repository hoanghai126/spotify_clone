package com.example.android.ui.fragment.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.adapter.GenresAdapter
import com.example.android.databinding.FragmentGenresBinding
import com.example.android.ui.fragment.BaseFragment
import com.example.android.viewmodel.ChannelViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GenresFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding: FragmentGenresBinding by lazy {
        FragmentGenresBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ChannelViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListGenres()

        return binding.root
    }

    private fun displayListGenres() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvData.layoutManager = layout

        val list = listOf("Pop", "R&B", "Rock", "Rap", "Country", "Blues", "Jazz")

        val adapter = GenresAdapter(list)
        binding.rcvData.adapter = adapter

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}