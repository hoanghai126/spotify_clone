package com.example.android.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import com.example.android.R
import com.example.android.databinding.ActivityMainBinding
import com.example.android.model.Song
import com.example.android.service.MusicService
import com.example.android.service.MusicServiceFunction

class MainActivity(override val toolBar: Toolbar? = null) : BaseActivity(), View.OnClickListener {

    private var selectedTab = 1

    companion object {
        private const val TAB_HOME = 1
        private const val TAB_LIBRARY = 2
        private const val TAB_HISTORY = 3
        private const val TAB_PROFILE = 4
    }

    private val imgTop by lazy {
        listOf(
            binding.imgTopHome,
            binding.imgTopPlaylist,
            binding.imgTopHistory,
            binding.imgTopProfile
        )
    }

    private val tvBottomBarTab by lazy {
        listOf(
            binding.tvHome,
            binding.tvPlaylist,
            binding.tvHistory,
            binding.tvProfile
        )
    }

    private val imgBottomBar by lazy {
        listOf(
            binding.imgHome,
            binding.imgPlaylist,
            binding.imgHistory,
            binding.imgProfile
        )
    }

    private val drawableResources = listOf(
        R.drawable.ic_home,
        R.drawable.ic_playlist,
        R.drawable.ic_history,
        R.drawable.ic_profile
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomBarViewListener()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mBroadcastReceiver,
            IntentFilter(MusicService.CHANGE_LISTENER)
        )

    }

    private fun selectTab(
        tab: Int,
        selectedImageId: Int,
        selectedTextView: TextView
    ) {
        if (selectedTab != tab) {

            imgTop.forEach { it.hide() }

            imgBottomBar.forEachIndexed { index, imageView ->
                imageView.setImageResourceCompat(drawableResources[index])
            }

            tvBottomBarTab.forEach { it.setTextColorRes(R.color.stroke_letter) }

            selectedTextView.setTextColorRes(R.color.green_text)

            val imageViewToHideAndSet = when (tab) {
                TAB_HOME -> binding.imgTopHome to binding.imgHome
                TAB_LIBRARY -> binding.imgTopPlaylist to binding.imgPlaylist
                TAB_HISTORY -> binding.imgTopHistory to binding.imgHistory
                TAB_PROFILE -> binding.imgTopProfile to binding.imgProfile
                else -> null to null
            }

            imageViewToHideAndSet.first?.hide()
            imageViewToHideAndSet.second?.setImageResourceCompat(selectedImageId)

            selectedTab = tab
        }
    }

    private fun setBottomBarViewListener() {
        binding.homeLayout.setOnClickListener {
            selectTab(TAB_HOME, R.drawable.ic_home_selected, binding.tvHome)
            findNavController(R.id.splash_nav_host_fragment).navigate(R.id.homepageFragment)
        }

        binding.playlistLayout.setOnClickListener {
            selectTab(
                TAB_LIBRARY,
                R.drawable.ic_playlist_selected,
                binding.tvPlaylist
            )
            findNavController(R.id.splash_nav_host_fragment).navigate(R.id.playlistFragment)
        }

        binding.historyLayout.setOnClickListener {
            selectTab(
                TAB_HISTORY,
                R.drawable.ic_clock_selected,
                binding.tvHistory
            )
            findNavController(R.id.splash_nav_host_fragment).navigate(R.id.historyFragment)
        }

        binding.profileLayout.setOnClickListener {
            selectTab(
                TAB_PROFILE,
                R.drawable.ic_profile_selected,
                binding.tvProfile
            )
            findNavController(R.id.splash_nav_host_fragment).navigate(R.id.profileFragment)

        }
    }

    private fun View.hide() {
        visibility = View.GONE
    }

    private fun TextView.setTextColorRes(colorRes: Int) {
        setTextColor(context.getColor(colorRes))
    }

    private fun ImageView.setImageResourceCompat(resourceId: Int) {
        setImageResource(resourceId)
    }

    private var action = -1

    private val mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            action = intent.getIntExtra(MusicService.MUSIC_ACTION, 0)
            handleMusicAction()
        }
    }

    private fun handleMusicAction() {
        if (MusicService.action == MusicService.CANCEL) {
            binding.controlBottom.layoutItem.visibility = View.GONE
            return
        } else {
            binding.controlBottom.layoutItem.visibility = View.VISIBLE
            Log.e("receive", "visible")
            showInforSong()
            showStatusButtonPlay()
        }
    }

    private fun showStatusButtonPlay() {
        if (!MusicService.isPlaying) {
            binding.controlBottom.imgPlay.setImageResource(R.drawable.ic_play_music)
        } else {
            binding.controlBottom.imgPlay.setImageResource(R.drawable.ic_pause_music)
        }
    }

    private fun showInforSong() {
        if (MusicService.listSongPlaying == null || MusicService.listSongPlaying!!.isEmpty()) {
            return
        }
        val currentSong: Song = MusicService.listSongPlaying!![MusicService.songPosition]
        binding.controlBottom.tvSongName.text = currentSong.nameSong
        binding.controlBottom.tvArtist.text = currentSong.artistSongName
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_previous -> clickOnPrevButton()
            R.id.img_play -> clickOnPlayButton()
            R.id.img_next -> clickOnNextButton()
            R.id.img_close -> clickOnCloseButton()
            R.id.layout_text, R.id.img_song -> openPlayMusicActivity()
        }
    }

    private fun openPlayMusicActivity() {
        MusicServiceFunction().startActivity(this, PlayMusicActivity::class.java)
    }

    private fun clickOnCloseButton() {
        MusicServiceFunction().startMusicService(
            this,
            MusicService.CANCEL,
            MusicService.songPosition
        )
    }

    private fun clickOnNextButton() {
        MusicServiceFunction().startMusicService(this, MusicService.NEXT, MusicService.songPosition)
    }

    private fun clickOnPlayButton() {
        if (MusicService.isPlaying) {
            MusicServiceFunction().startMusicService(
                this,
                MusicService.PAUSE,
                MusicService.songPosition
            )
        } else {
            MusicServiceFunction().startMusicService(
                this,
                MusicService.RESUME,
                MusicService.songPosition
            )
        }
    }

    private fun clickOnPrevButton() {
        MusicServiceFunction().startMusicService(
            this,
            MusicService.PREVIOUS,
            MusicService.songPosition
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver)
    }

}

