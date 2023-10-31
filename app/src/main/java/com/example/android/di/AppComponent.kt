package com.example.android.di

import com.example.android.di.module.AppModule
import com.example.external.di.RepositoriesModule
import com.example.external.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoriesModule::class
            ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: android.app.Application): Builder

        fun networkModule(scootNetworkModule: NetworkModule): Builder

        fun build(): AppComponent
    }

    fun inject(application: com.example.android.Application)
}
