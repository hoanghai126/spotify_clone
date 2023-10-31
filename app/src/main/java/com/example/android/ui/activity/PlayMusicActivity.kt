package com.example.android.ui.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.android.R
import com.example.android.databinding.ActivityPlayMusicBinding
import com.example.android.ui.fragment.playing_music.MusicPageFragment

internal class PlayMusicActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMusicBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        val fragment = MusicPageFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    override val toolBar: Toolbar?
        get() = null
}