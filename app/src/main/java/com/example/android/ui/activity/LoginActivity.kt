package com.example.android.ui.activity

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.util.forEach
import androidx.core.util.size
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.LoginActivityBinding
import com.example.android.viewmodel.LoginViewModel
import com.maxrave.kotlinyoutubeextractor.State
import com.maxrave.kotlinyoutubeextractor.VideoMeta
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import com.maxrave.kotlinyoutubeextractor.YtFile
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : BaseActivity(), HasAndroidInjector {
    private lateinit var binding: LoginActivityBinding

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override val toolBar: Toolbar?
        get() = null

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpViews()
        viewModel.isLoading.observe(this) {
            if (it) {
                loading()
            } else {
                dismiss()
            }
        }

        viewModel.logInResult.observe(this) {
            if (it) {
                Log.d("#PhucNK1 received ", it.toString())
                Toast.makeText(this@LoginActivity, "Success Login", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("#PhucNK1 received ", it.toString())
                Toast.makeText(this@LoginActivity, "Failed Login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpViews() {
        binding.signIn.setOnClickListener {
            //If your YouTube link is "https://www.youtube.com/watch?v=IDwytT0wFRM" so this videoId is "IDwytT0wFRM"
            val videoId = "uelHwf8o7_U"
            val yt = YTExtractor(con = this, CACHING = true, LOGGING = true, retryCount = 3)
            var url = ""
            var found = false
            // CACHING and LOGGING are 2 optional params. LOGGING is for showing Log and CACHING is for saving SignatureCipher to optimize extracting time (not recommend CACHING to extract multiple videos because it causes HTTP 403 Error)
            // retryCount is for retrying when extract fail (default is 1)
            var ytFiles: SparseArray<YtFile>? = null
            var videoMeta: VideoMeta? = null
            CoroutineScope(Dispatchers.Main).launch {
                yt.extract(videoId)
                //Before get YtFile or VideoMeta, you need to check state of yt object
                if (yt.state == State.SUCCESS) {
                    ytFiles = yt.getYTFiles()
                    videoMeta = yt.getVideoMeta()
//                    ytFiles?.forEach { key, value ->
//                        url = value.url.toString()
//                        Log.d("ytExtract", url)
//                        return@forEach
//                    }
                    ytFiles?.forEach { key, value ->
                        if (!found) {
                            url = value.url.toString()
                            Log.d("ytExtract2", url)
                            found = true
                        }
                    }
//                    url = ytFiles?.get(0)?.url.toString()
//                    Log.d("ytExtract", url)


                }
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}