package com.example.external.channel

import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelService {
    @GET("channels")
    suspend fun getInfoChannel(
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("key") key: String
    ): ChannelEntity

    @GET("search")
    suspend fun searchChannelInfo(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("type") type: String,
        @Query("q") q: String,
        @Query("maxResults") maxResults: Int,
        @Query("order") order: String,
    ): SearchChannelEntity

    @GET("search")
    suspend fun searchTopHitVideo(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("type") type: String,
        @Query("q") q: String,
        @Query("maxResults") maxResults: Int,
        @Query("order") order: String,
    ): SearchChannelEntity

    @GET("playlists")
    suspend fun getAlbumArtistInfo(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("channelId") type: String,
        @Query("maxResults") maxResults: Int,
    ): SearchChannelEntity

    @GET("search")
    suspend fun getSongArtistInfo(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("order") order: String,
        @Query("channelId") channelId: String,
    ): SearchChannelEntity

    @GET("search")
    suspend fun getAlbumFragment(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("q") q: String
        ): SearchChannelEntity

    @GET("search")
    suspend fun getPodcastFragment(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("q") q: String
    ): SearchChannelEntity

//    @GET("search")
//    suspend fun getAlbumFragment(
//        @Query("part") part: String,
//        @Query("key") key: String,
//        @Query("maxResults") maxResults: Int,
//        @Query("order") order: String,
//        @Query("type") type: String,
//    ): SearchChannelEntity
}