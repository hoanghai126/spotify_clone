package com.example.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.ui.fragment.bottom_nav.AlbumFragment
import com.example.android.ui.fragment.login.GetStartedFragment
import com.example.android.ui.fragment.bottom_nav.ArtistFragment
import com.example.android.ui.fragment.bottom_nav.GenresFragment
import com.example.android.ui.fragment.bottom_nav.PodcastFragment
import com.example.android.ui.fragment.login.LoginFragment
import com.example.android.ui.fragment.login.RegisterFragment

class FragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ArtistFragment()
            1 -> AlbumFragment()
            2 -> PodcastFragment()
            else -> GenresFragment()
        }
    }
}