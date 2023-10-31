package com.example.external.di

import android.app.Application
import com.example.external.provider.LoginService
import com.example.external.channel.ChannelService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Named
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val Application = "Application"

@Module
@Suppress("UNUSED")
class NetworkModule() {

//    private var baseAuthUrl = "https://run.mocky.io/"

    private var baseAuthUrl = "https://www.googleapis.com/youtube/v3/"

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideCache(application: Application): Cache {
        val cacheSize = 1024 * 1024 * 10L
        return Cache(application.cacheDir, cacheSize)
    }


    @Provides
    @Named(Application)
    fun provideOkHttpClient(cache: Cache): OkHttpClient {

        val url = URL(this.baseAuthUrl)

        // HttpsUrlConnection
        url.openConnection()

        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .hostnameVerifier { _, _ -> true }

        return builder.build()
    }


    @Provides
    @Named(Application)
    fun provideRetrofit(@Named(Application) httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(this.baseAuthUrl)
            .build()
    }

    @Provides
    fun provideLoginService(@Named(Application) retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    fun provideChannelService(@Named(Application) retrofit: Retrofit): ChannelService =
        retrofit.create(ChannelService::class.java)
}
