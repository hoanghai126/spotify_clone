package com.example.android.service

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.android.R
import com.example.android.model.Song

class MusicService : Service(), OnPreparedListener, OnCompletionListener {
    companion object {
        var listSongPlaying: MutableList<Song>? = null
        var songPosition = 0
        var songLength = 0
        var action = -1
        var isPlaying = false
        var mediaPlayer: MediaPlayer? = null

        const val PLAY = 0
        const val PAUSE = 1
        const val NEXT = 2
        const val PREVIOUS = 3
        const val RESUME = 4
        const val CANCEL = 5
        const val MUSIC_ACTION = "musicAction"
        const val SONG_POSITION = "songPosition"
        const val CHANGE_LISTENER = "changeListener"

        fun clearListSongPlaying() {
            if (listSongPlaying != null) {
                listSongPlaying!!.clear()
            } else {
                listSongPlaying = mutableListOf()
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            Log.e("test", "create")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras
        if (bundle != null) {
            if (bundle.containsKey(MUSIC_ACTION)) {
                action = bundle.getInt(MUSIC_ACTION)
            }
            if (bundle.containsKey(SONG_POSITION)) {
                songPosition = bundle.getInt(SONG_POSITION)
            }
            handleActionMusic(action)
        }

        return START_NOT_STICKY
    }

    private fun handleActionMusic(action: Int) {
        when (action) {
            PLAY -> playSong()
            PAUSE -> pauseSong()
            NEXT -> nextSong()
            PREVIOUS -> prevSong()
            RESUME -> resumeSong()
            CANCEL -> cancelNotification()
            else -> {}
        }
    }

    private fun cancelNotification() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
        }
        val notification = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notification.cancelAll()
        sendBroadcastChangeListener()
        stopSelf()
    }

    private fun resumeSong() {
        if (mediaPlayer != null) {
            mediaPlayer?.start()
            isPlaying = true
            sendMusicPushNotification()
            sendBroadcastChangeListener()
        }
    }

    private fun prevSong() {
        if (listSongPlaying?.size!! > 1) {
            if (songPosition > 0) {
                songPosition--
            } else {
                //move to last song
                songPosition = listSongPlaying?.size!! - 1
            }
        } else {
            //move to first song
            songPosition = 0
        }
        sendMusicPushNotification()
        sendBroadcastChangeListener()
        playSong()
    }

    private fun nextSong() {
        if (listSongPlaying?.size!! > 1 && songPosition < listSongPlaying?.size!! - 1) {
            songPosition++
        } else {
            songPosition = 0
        }
        sendMusicPushNotification()
        sendBroadcastChangeListener()
        playSong()
    }

    private fun pauseSong() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            sendMusicPushNotification()
            sendBroadcastChangeListener()
        }
    }

    private fun sendBroadcastChangeListener() {
        val intent = Intent(CHANGE_LISTENER)
        intent.putExtra(MUSIC_ACTION, action)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun playSong() {
        val songUrl: String = listSongPlaying?.get(songPosition)!!.url
        playMediaPlayer(songUrl)
        isPlaying = true
    }

    private fun playMediaPlayer(songUrl: String?) {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer!!.stop()
            }
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(songUrl)
            mediaPlayer?.prepareAsync()
            initControl()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initControl() {
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setOnCompletionListener(this)
    }

    private fun sendMusicPushNotification() {
        val song: Song = listSongPlaying!![songPosition]

        val remoteViews = RemoteViews(packageName, R.layout.layout_push_notification_music)

        remoteViews.setTextViewText(R.id.tv_song_name, song.nameSong)
        remoteViews.setTextViewText(R.id.tv_artist, song.artistSongName)

        // Set listener
        remoteViews.setOnClickPendingIntent(
            R.id.img_previous,
            MusicServiceFunction().openMusicReceiver(this, PREVIOUS),
        )
        remoteViews.setOnClickPendingIntent(
            R.id.img_next,
            MusicServiceFunction().openMusicReceiver(this, NEXT)
        )
        if (isPlaying) {
            remoteViews.setImageViewResource(R.id.img_play, R.drawable.ic_pause_music)
            remoteViews.setOnClickPendingIntent(
                R.id.img_play,
                MusicServiceFunction().openMusicReceiver(this, PAUSE)
            )
        } else {
            remoteViews.setImageViewResource(R.id.img_play, R.drawable.ic_play_music)
            remoteViews.setOnClickPendingIntent(
                R.id.img_play,
                MusicServiceFunction().openMusicReceiver(this, RESUME)
            )
        }
        remoteViews.setOnClickPendingIntent(
            R.id.img_close,
            MusicServiceFunction().openMusicReceiver(this, CANCEL)
        )

        // Create the notification
        val notification: Notification = NotificationCompat.Builder(this, "channel_id")
            .setCustomContentView(remoteViews)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.app_icon)
            .setSound(null)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        startForeground(1, notification)

    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
        Log.e("test", "destroy")
    }


    override fun onPrepared(mp: MediaPlayer?) {
        songLength = mediaPlayer!!.duration
        mp?.start()
        isPlaying = true
        action = PLAY
        sendMusicPushNotification()
        sendBroadcastChangeListener()
        Log.e("service", "prepare")

    }

    override fun onCompletion(p0: MediaPlayer?) {
        action = NEXT
        nextSong()
        Log.e("service", "completion")
    }

}