package com.example.android.ui.fragment.playing_music

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.databinding.FragmentMusicPageBinding
import com.example.android.model.Song
import com.example.android.service.MusicService
import com.example.android.service.MusicServiceFunction
import com.example.android.ui.fragment.BaseFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*
import javax.inject.Inject

class MusicPageFragment : BaseFragment(), HasAndroidInjector, View.OnClickListener {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding: FragmentMusicPageBinding by lazy {
        FragmentMusicPageBinding.inflate(layoutInflater)
    }

    private var timer: Timer? = null
    private var action = 0

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            action = intent.getIntExtra(MusicService.MUSIC_ACTION, 0)
            handleMusicAction()
        }
    }

    private fun handleMusicAction() {
        when (action) {
            MusicService.PREVIOUS, MusicService.NEXT -> {
                showSongInformation()
            }
            MusicService.PLAY -> {
                showSongInformation()
                showSeekBar()
                showStatusButtonPlay()
            }
            MusicService.PAUSE -> {
                showSeekBar()
                showStatusButtonPlay()
            }
            MusicService.RESUME -> {
                showSeekBar()
                showStatusButtonPlay()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (activity != null) {
            LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(
                broadcastReceiver,
                IntentFilter(MusicService.CHANGE_LISTENER)
            )
        }

        initControl()
        showSongInformation()
        action = MusicService.action
        handleMusicAction()

        return binding.root
    }

    private fun showSongInformation() {
        if (MusicService.listSongPlaying == null || MusicService.listSongPlaying!!.isEmpty()) {
            return
        }
        val currentSong: Song = MusicService.listSongPlaying!![MusicService.songPosition]
        binding.tvSongName.text = currentSong.nameSong
        binding.tvArtistName.text = currentSong.artistSongName
        Glide.with(binding.root)
            .load(currentSong.imageSong)
            .error(R.drawable.image_not_available)
            .into(binding.artistImg)
    }

    private fun initControl() {
        timer = Timer()

        binding.icPrevious.setOnClickListener(this)
        binding.icPlayPause.setOnClickListener(this)
        binding.icNext.setOnClickListener(this)
        binding.icRepeat.setOnClickListener(this)
        binding.icShuffle.setOnClickListener(this)

        binding.seekbar.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                MusicService.mediaPlayer!!.seekTo(seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
        })
    }

    private fun showStatusButtonPlay() {
        if (MusicService.isPlaying) {
            binding.icPlayPause.setImageResource(R.drawable.ic_pause_music)
        } else {
            binding.icPlayPause.setImageResource(R.drawable.ic_play_music)
        }
    }

    private fun showSeekBar() {
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                if (activity == null) {
                    return
                }
                activity!!.runOnUiThread {
                    if (MusicService.mediaPlayer == null) {
                        return@runOnUiThread
                    }
                    binding.tvStartDuration.text =
                        MusicServiceFunction().getTime(MusicService.mediaPlayer!!.currentPosition)
                    binding.tvEndDuration.text =
                        MusicServiceFunction().getTime(MusicService.songLength)
                    binding.seekbar.max = MusicService.songLength
                    binding.seekbar.progress = MusicService.mediaPlayer!!.currentPosition
                }
            }
        }, 0, 1000)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.icPrevious -> clickOnPrevButton()
            R.id.icPlayPause -> clickOnPlayButton()
            R.id.icNext -> clickOnNextButton()
            R.id.icRepeat -> clickOnLoopButton()
            R.id.icShuffle -> clickOnShuffleButton()
            else -> {}
        }
    }

    private fun clickOnShuffleButton() {

    }

    private fun clickOnLoopButton() {

    }

    private fun clickOnNextButton() {
        MusicServiceFunction().startMusicService(
            requireContext(),
            MusicService.NEXT,
            MusicService.songPosition
        )
    }

    private fun clickOnPlayButton() {
        if (MusicService.isPlaying) {
            MusicServiceFunction().startMusicService(
                requireContext(),
                MusicService.PAUSE,
                MusicService.songPosition
            )
        } else {
            MusicServiceFunction().startMusicService(
                requireContext(),
                MusicService.RESUME,
                MusicService.songPosition
            )
        }
    }

    private fun clickOnPrevButton() {
        MusicServiceFunction().startMusicService(
            requireContext(),
            MusicService.PREVIOUS,
            MusicService.songPosition
        )

    }

}