package com.example.domain.channel

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.domain.result.Result

class ChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(): Flow<Result<SearchChannelResponseDomain>> =
        channelRepository.searchChannelInfo()

    suspend fun getChannelInfo(channelId: String): Flow<Result<ChannelResponseDomain>> =
        channelRepository.getChannelInfo(channelId)

    suspend fun searchHitVideoInfo(): Flow<Result<SearchChannelResponseDomain>> =
        channelRepository.searchTopHitVideo()

    suspend fun getAlbumArtistInfo(channelId: String): Flow<Result<SearchChannelResponseDomain>> =
        channelRepository.getAlbumArtistInfo(channelId)

    suspend fun getSongArtistInfo(channelId: String): Flow<Result<SearchChannelResponseDomain>> =
        channelRepository.getSongArtistInfo(channelId)

    suspend fun getAlbumFragment(): Flow<Result<SearchChannelResponseDomain>> =
        channelRepository.getAlbumFragment()

    suspend fun getPodcastFragment(): Flow<Result<SearchChannelResponseDomain>> =
        channelRepository.getPodcastFragment()

}