package com.example.android.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MusicReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val action: Int = intent.extras?.getInt(MusicService.MUSIC_ACTION) as Int
        MusicServiceFunction().startMusicService(context!!, action, MusicService.songPosition)
    }

}