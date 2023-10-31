package com.example.domain.channel

import kotlinx.coroutines.flow.Flow
import com.example.domain.result.Result

interface ChannelRepository {
    suspend fun searchChannelInfo(): Flow<Result<SearchChannelResponseDomain>>
    suspend fun searchTopHitVideo(): Flow<Result<SearchChannelResponseDomain>>
    suspend fun getChannelInfo(channelId: String): Flow<Result<ChannelResponseDomain>>
    suspend fun getAlbumArtistInfo(channelId: String): Flow<Result<SearchChannelResponseDomain>>
    suspend fun getSongArtistInfo(channelId: String): Flow<Result<SearchChannelResponseDomain>>
    suspend fun getAlbumFragment(): Flow<Result<SearchChannelResponseDomain>>
    suspend fun getPodcastFragment(): Flow<Result<SearchChannelResponseDomain>>



}
