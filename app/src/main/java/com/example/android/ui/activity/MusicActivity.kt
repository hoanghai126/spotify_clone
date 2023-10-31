package com.example.android.ui.activity

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.R
import com.example.android.adapter.SongAdapter
import com.example.android.databinding.FragmentMusicBinding
import com.example.android.listeners.IOnClickSongItemListener
import com.example.android.model.Artist
import com.example.android.model.Song
import com.example.android.service.MusicService
import com.example.android.service.MusicServiceFunction


class MusicActivity : AppCompatActivity(), View.OnClickListener {

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

    private lateinit var binding: FragmentMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mBroadcastReceiver,
            IntentFilter(MusicService.CHANGE_LISTENER)
        )

//        val intent = Intent(this, MusicService::class.java)
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        displayListSongs()
    }

    private fun displayListSongs() {
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSongs.layoutManager = layout
        val adapter = SongAdapter(object : IOnClickSongItemListener {
            override fun onClickItemArtist(artist: Artist) {
                TODO("Not yet implemented")
            }

            override fun onClickItemSong(song: Song) {
                goToSongDetail(song)
            }

        })
        binding.recyclerViewSongs.adapter = adapter

        adapter.submitList(getSong())

    }

    private fun getSong(): List<Song> {
        val list = mutableListOf<Song>()

        list.add(
            Song(
                0,
                "https://vtv1.mediacdn.vn/zoom/640_400/2022/12/13/131222-adele-1670905139752141068015-crop-1670905144383835571127.png",
                "Hello",
                "Adele",
                "https://archive.org/download/y-2meta.com-neu-nhu-anh-noi-ra-ducbacvu-official-128-kbps/y2meta.com%20-%20Ne%CC%82%CC%81u%20Nhu%CC%9B%20Anh%20No%CC%81i%20Ra%20-%20Ducbacvu%20_%20OFFICIAL%20%28128%20kbps%29.mp3"
            )
        )

        list.add(
            Song(
                0,
                "https://vtv1.mediacdn.vn/zoom/640_400/2022/12/13/131222-adele-1670905139752141068015-crop-1670905144383835571127.png",
                "Hello",
                "Adele",
                "https://archive.org/download/y-2meta.com-neu-nhu-anh-noi-ra-ducbacvu-official-128-kbps/y2meta.com%20-%20Ne%CC%82%CC%81u%20Nhu%CC%9B%20Anh%20No%CC%81i%20Ra%20-%20Ducbacvu%20_%20OFFICIAL%20%28128%20kbps%29.mp3"
            )
        )

        return list
    }

    private fun goToSongDetail(song: Song) {
        MusicService.clearListSongPlaying()
        MusicService.listSongPlaying?.add(song)
        MusicService.isPlaying = false
        MusicServiceFunction().startMusicService(this, MusicService.PLAY, 0)

        MusicServiceFunction().startActivity(this, PlayMusicActivity::class.java)
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
