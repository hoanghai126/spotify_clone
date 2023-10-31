package com.example.android.ui.fragment.bottom_nav

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.android.R
import com.example.android.databinding.FragmentHomepageBinding
import com.example.android.adapter.FragmentPageAdapter
import com.example.android.ui.fragment.BaseFragment
import com.example.android.viewmodel.ChannelViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.login_activity.view.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class HomepageFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val homepageBinding: FragmentHomepageBinding by lazy {
        FragmentHomepageBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ChannelViewModel::class.java)
    }

    private lateinit var adapter: FragmentPageAdapter

    private var tabStates: MutableList<Boolean> = mutableListOf()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initTabLayoutUI()
        displayListTodayHits()

        // Inflate the layout for this fragment
        return homepageBinding.root
    }

    private fun initTabLayoutUI() {
        adapter = FragmentPageAdapter(requireFragmentManager(), lifecycle)
        homepageBinding.tabLayout.addTab(homepageBinding.tabLayout.newTab().setText("Artists"))
        homepageBinding.tabLayout.addTab(homepageBinding.tabLayout.newTab().setText("Album"))
        homepageBinding.tabLayout.addTab(homepageBinding.tabLayout.newTab().setText("Podcast"))
        homepageBinding.tabLayout.addTab(homepageBinding.tabLayout.newTab().setText("Genre"))

        homepageBinding.viewpager2Homepage.adapter = adapter

        for (i in 0 until homepageBinding.tabLayout.tabCount) {
            if (i == 0) {
                tabStates.add(true)
                setCustomSelectedTabLayout(0)
            } else {
                setCustomUnselectedTabLayout(i)
                tabStates.add(false)
            }
        }

        homepageBinding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    homepageBinding.viewpager2Homepage.currentItem = tab.position
                    tabStates[tab.position] = true
                    updateTabViews()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    tabStates[tab.position] = false
                    updateTabViews()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        homepageBinding.viewpager2Homepage.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                homepageBinding.tabLayout.selectTab(homepageBinding.tabLayout.getTabAt(position))
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun setCustomSelectedTabLayout(position: Int) {
        val tab = homepageBinding.tabLayout.getTabAt(position)
        val customView = layoutInflater.inflate(R.layout.custom_tab_layout_selected, null)
        val tabTextSelected: TextView = customView.findViewById(R.id.tabTextSelected)
        tabTextSelected.text = tab?.text
        tab?.customView = customView
    }

    @SuppressLint("InflateParams")
    private fun setCustomUnselectedTabLayout(position: Int) {
        val tab = homepageBinding.tabLayout.getTabAt(position)
        val customView = layoutInflater.inflate(R.layout.custom_tab_layout_unselected, null)
        val tabTextUnselected: TextView = customView.findViewById(R.id.tabTextUnselected)
        tabTextUnselected.text = tab?.text
        tab?.customView = customView
    }

    private fun updateTabViews() {
        for (i in 0 until homepageBinding.tabLayout.tabCount) {
            val tab = homepageBinding.tabLayout.getTabAt(i)
            if (tab != null) {
                tab.customView = null
                if (tabStates[i]) {
                    setCustomSelectedTabLayout(i)
                } else {
                    setCustomUnselectedTabLayout(i)
                }
            }
        }
    }

    private fun displayListTodayHits() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homepageBinding.rcvData.layoutManager = layout
//        val adapter = SongAdapter()
//        homepageBinding.rcvData.adapter = adapter

//        adapter.submitList(getListSongs())

//        viewModel.topHitSong.observe(viewLifecycleOwner){ songList ->
//            adapter.submitList(songList)
//        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}