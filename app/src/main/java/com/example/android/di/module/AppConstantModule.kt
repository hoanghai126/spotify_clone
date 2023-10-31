package com.example.android.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
@Suppress("UNUSED")
class AppConstantModule {

    @Provides
    @Named("snippet")
    fun provideSnippetConstant(): String = "snippet"

    @Provides
    @Named("snippet_statistic")
    fun provideSnippetStatisticsConstant(): String = "snippet,statistics"

    @Provides
    @Named("apiKey")
    fun provideApiKeyConstant(): String = "AIzaSyBV7RM1z-3NeAxn8-jZktdYqLT5TqrbNXI"

    @Provides
    @Named("channelType")
    fun provideChannelTypeConstant(): String = "channel"

    @Provides
    @Named("videoType")
    fun provideVideoConstant(): String = "video"

    @Provides
    @Named("defaultItemsPerPage")
    fun provideDefaultItemsPerPageConstant(): Int = 1

    @Provides
    @Named("orderRelevance")
    fun provideOrderRelevanceConstant(): String = "relevance"

    @Provides
    @Named("orderViewCount")
    fun provideOrderViewCountConstant(): String = "viewCount"

    @Provides
    @Named("maxResults")
    fun provideMaxResultsConstant(): Int = 10

    @Provides
    @Named("songType1")
    fun provideSongType1Constant(): Int = 0

    @Provides
    @Named("songType2")
    fun provideSongType2Constant(): Int = 1

    @Provides
    @Named("songType3")
    fun provideSongType3Constant(): Int = 2

}
