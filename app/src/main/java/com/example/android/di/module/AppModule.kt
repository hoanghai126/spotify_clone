package com.example.android.di.module

import android.app.Application
import android.content.Context
import com.example.domain.di.CoroutinesModule
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        AppViewModelModule::class,
        AppActivityModule::class,
        AppFragmentModule::class,
        CoroutinesModule::class,
        AppConstantModule::class,
        FirestoreModule::class
    ]
)
abstract class AppModule {
    @Binds
    abstract fun provideContext(application: Application): Context

}
