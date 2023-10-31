package com.example.android.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class MusicServiceFunction {
    fun startMusicService(context: Context, action: Int, songPosition: Int) {
        val musicService = Intent(context, MusicService::class.java)
        musicService.putExtra(MusicService.MUSIC_ACTION, action)
        musicService.putExtra(MusicService.SONG_POSITION, songPosition)
        context.startService(musicService)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun openMusicReceiver(ctx: Context, action: Int): PendingIntent? {
        val intent = Intent(ctx, MusicReceiver::class.java)
        intent.putExtra(MusicService.MUSIC_ACTION, action)
        val pendingFlag: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(ctx.applicationContext, action, intent, pendingFlag)
    }

    @SuppressLint("DefaultLocale")
    fun getTime(millis: Int): String {
        val second = (millis / 1000 % 60).toLong()
        val minute = (millis / (1000 * 60)).toLong()
        return String.format("%02d:%02d", minute, second)
    }

    fun startActivity(context: Context, cls: Class<*>?) {
        val intent = Intent(context, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}