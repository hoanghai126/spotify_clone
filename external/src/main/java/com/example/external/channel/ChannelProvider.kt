package com.example.external.channel

import android.util.Log
import com.example.domain.di.DefaultDispatcher
import com.example.domain.result.Result
import com.example.domain.channel.ChannelRepository
import com.example.domain.channel.ChannelResponseDomain
import com.example.domain.channel.SearchChannelResponseDomain
import com.example.external.provider.BaseProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Named

class ChannelProvider @Inject constructor(
    private val channelService: ChannelService,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @Named("snippet") private val snippet: String,
    @Named("snippet_statistic") private val snippet2: String,
    @Named("apiKey") private val apiKey: String,
    @Named("channelType") private val channelType: String,
    @Named("videoType") private val videoType: String,
    @Named("defaultItemsPerPage") private val defaultItemsPerPage: Int,
    @Named("orderRelevance") private val orderRelevance: String,
    @Named("orderViewCount") private val orderViewCount: String,
    @Named("maxResults") private val maxResults: Int
) : ChannelRepository, BaseProvider(defaultDispatcher) {

    private val listChannelInfo = mutableListOf<SearchChannelEntity>()

    // get artist in tab artist in homepage fragment
    override suspend fun searchChannelInfo(): Flow<Result<SearchChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                val artistNames = getData()
                for (name in artistNames) {
                    val response = channelService.searchChannelInfo(
                        snippet,
                        apiKey,
                        channelType,
                        name,
                        defaultItemsPerPage,
                        orderRelevance
                    )
                    listChannelInfo.add(response)
                }
                listChannelInfo
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    val responseItems = mutableListOf<SearchChannelResponseDomain.Items>()

                    for (entity in it.data) {
                        val mappedItems = entity.items.map { item ->
                            SearchChannelResponseDomain.Items(
                                snippet = item.snippet.mapSnippetYtToDomain(item.snippet)
                            )
                        }
                        responseItems.addAll(mappedItems)
                    }

                    Result.Success(SearchChannelResponseDomain(items = responseItems))
                }

                else -> Result.Success(SearchChannelResponseDomain())
            }
        }
    }

    //get top view video ~ top hit song in homepage fragment
    override suspend fun searchTopHitVideo(): Flow<Result<SearchChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                val response = channelService.searchTopHitVideo(
                    snippet,
                    apiKey,
                    videoType,
                    "music",
                    1,
                    orderViewCount
                )
                response
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    val responseItems = mutableListOf<SearchChannelResponseDomain.Items>()

                    for (entity in it.data.items) {
                        val item = SearchChannelResponseDomain.Items(
                            id = entity.id.mapIdYtToDomain(entity.id),
                            snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet)
                        )

                        responseItems.addAll(listOf(item))
                    }


                    Result.Success(SearchChannelResponseDomain(items = responseItems))
                }

                else -> Result.Success(SearchChannelResponseDomain())
            }
        }
    }

    //get channel info to artist info fragment
    override suspend fun getChannelInfo(channelId: String): Flow<Result<ChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                channelService.getInfoChannel(
                    snippet2, channelId, apiKey
                )
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    Result.Success(
                        ChannelResponseDomain(
                            items = it.data.mapChannelEntityListToDomain(it.data.items)
                        )
                    )
                }

                else -> Result.Success(ChannelResponseDomain())
            }
        }
    }

    //get all playlist of a channel ~ album in artist info fragment
    override suspend fun getAlbumArtistInfo(channelId: String): Flow<Result<SearchChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                channelService.getAlbumArtistInfo(
                    snippet, apiKey, channelId, 1
                )
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    val responseItems = mutableListOf<SearchChannelResponseDomain.Items>()

                    for (entity in it.data.items) {
                        val item = SearchChannelResponseDomain.Items(
                            id = entity.id.mapIdYtToDomain(entity.id),
                            snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet)
                        )

                        responseItems.addAll(listOf(item))
                    }


                    Result.Success(SearchChannelResponseDomain(items = responseItems))
                }

                else -> Result.Success(SearchChannelResponseDomain())
            }
        }
    }

    override suspend fun getSongArtistInfo(channelId: String): Flow<Result<SearchChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                channelService.getSongArtistInfo(
                    snippet, apiKey, 1, orderViewCount, channelId
                )
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    val responseItems = mutableListOf<SearchChannelResponseDomain.Items>()

                    for (entity in it.data.items) {
                        val item = SearchChannelResponseDomain.Items(
                            id = entity.id.mapIdYtToDomain(entity.id),
                            snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet)
                        )

                        responseItems.addAll(listOf(item))
                    }


                    Result.Success(SearchChannelResponseDomain(items = responseItems))
                }

                else -> Result.Success(SearchChannelResponseDomain())
            }
        }
    }

    override suspend fun getAlbumFragment(): Flow<Result<SearchChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                channelService.getAlbumFragment(
                    snippet, apiKey, 1, orderViewCount, "playlist", "music"
                )
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    val responseItems = mutableListOf<SearchChannelResponseDomain.Items>()

                    for (entity in it.data.items) {
                        val item = SearchChannelResponseDomain.Items(
                            id = entity.id.mapIdYtToDomain(entity.id),
                            snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet)
                        )

                        responseItems.addAll(listOf(item))
                    }


                    Result.Success(SearchChannelResponseDomain(items = responseItems))
                }

                else -> Result.Success(SearchChannelResponseDomain())
            }
        }
    }

    override suspend fun getPodcastFragment(): Flow<Result<SearchChannelResponseDomain>> {
        return flow {
            emit(safeApiCall {
                channelService.getPodcastFragment(
                    snippet, apiKey, 1, "rating", "video", "podcast"
                )
            })
        }.map {
            when (it) {
                is Result.Success -> {
                    val responseItems = mutableListOf<SearchChannelResponseDomain.Items>()

                    for (entity in it.data.items) {
                        val item = SearchChannelResponseDomain.Items(
                            id = entity.id.mapIdYtToDomain(entity.id),
                            snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet)
                        )

                        responseItems.addAll(listOf(item))
                    }


                    Result.Success(SearchChannelResponseDomain(items = responseItems))
                }

                else -> Result.Success(SearchChannelResponseDomain())
            }
        }
    }

    private suspend fun getData(): List<String> {
        val db = FirebaseFirestore.getInstance()
        val artistNames = mutableListOf<String>()

        try {
            val result = db.collection("Artist").get().await()
            for (document in result) {
                val artistName = document.getString("name")
                artistName?.let {
                    artistNames.add(it)
                }
            }
        } catch (exception: Exception) {
            Log.e("error", "Error getting documents.", exception)
        }

        return artistNames
    }


}