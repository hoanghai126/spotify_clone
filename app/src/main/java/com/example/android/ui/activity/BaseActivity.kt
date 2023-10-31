package com.example.android.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.android.ui.fragment.LoadingFragment
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var loadingFragment: LoadingFragment? = null

    abstract val toolBar: Toolbar?

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initAppBar()
    }

    fun initAppBar() {
        toolBar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun loading() {
        if (loadingFragment?.isDetached == false) return
        if (loadingFragment == null && !this.isFinishing) {
            loadingFragment = LoadingFragment.newInstance()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(loadingFragment!!, LoadingFragment.TAG)
        transaction.commitAllowingStateLoss()
    }

    fun dismiss() {
        loadingFragment?.dismiss()
        loadingFragment = null
    }
}