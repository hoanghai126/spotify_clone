package com.example.android.viewmodel

import android.content.Context
import android.util.Log
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.adapter.SongViewType
import com.example.android.model.Album
import com.example.android.model.Artist
import com.example.android.model.Song
import com.example.domain.channel.ChannelResponseDomain
import javax.inject.Inject
import com.example.domain.result.Result
import com.example.domain.channel.ChannelUseCase
import com.example.domain.channel.SearchChannelResponseDomain
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.maxrave.kotlinyoutubeextractor.State
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import com.maxrave.kotlinyoutubeextractor.YtFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChannelViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
    private val db: FirebaseFirestore,
    private val context: Context
) : ViewModel() {
    private val mutableIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    private val mutableChannelResult = MutableLiveData<Boolean>()
    val channelResult: LiveData<Boolean>
        get() = mutableChannelResult

    private val _channel = MutableLiveData<ChannelResponseDomain>()
    val channel = _channel

    private val _topHitSong = MutableLiveData<List<Song>>()
    val topHitSong = _topHitSong

    private val _artistList = MutableLiveData<List<Artist>>()
    val artistList = _artistList

    private val _genresFragment = MutableLiveData<List<String>>()
    val genresFragment = _genresFragment

    private val _songArtistInfoList = MutableLiveData<List<Song>>()
    val songArtistInfoList = _songArtistInfoList

    private val _albumArtistInfoList = MutableLiveData<List<Album>>()
    val albumArtistInfoList = _albumArtistInfoList

    private val _albumFragment = MutableLiveData<List<Album>>()
    val albumFragment = _albumFragment

    private val _podcastFragment = MutableLiveData<List<Song>>()
    val podcastFragment = _podcastFragment

    private val _artistsFromFirestore = mutableListOf<Artist>()

    private val convertArtistList = mutableListOf<Artist>()
    private val convertTopHitList = mutableListOf<Song>()

    //    private val convertPlaylistList = mutableListOf<Playlist>()
    private val convertAlbumArtistInfoList = mutableListOf<Album>()
    private val convertSongArtistInfoList = mutableListOf<Song>()

    private fun startLoading() {
        mutableIsLoading.postValue(true)
    }

    private fun stopLoading() {
        mutableIsLoading.postValue(false)
    }

    init {
        getSearchChannelInfo()
        loadArtistsFromFirestore()
//        searchTopHitVideo()
    }

    private fun getSearchChannelInfo() {
        viewModelScope.launch {
            val documentArtistCount = getDocumentCountInCollection("Artist")
            channelUseCase.invoke()
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            mutableChannelResult.value = true
                            if (_artistsFromFirestore.isEmpty()) {
                                _artistList.value = convertToArtistList(it.data)
                                saveDataToFirestore(it.data)
                            } else if (_artistsFromFirestore.size < documentArtistCount) {
                                deleteCollection("SearchChannel")
                                _artistList.value = convertToArtistList(it.data)
                                saveDataToFirestore(it.data)
                            } else {
                                _artistList.value = _artistsFromFirestore

                            }
                        }
                        else -> mutableChannelResult.value = false
                    }
                }
        }
    }

    fun getChannelInfo(channelId: String) {
        viewModelScope.launch {
            channelUseCase.getChannelInfo(channelId)
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            mutableChannelResult.value = true
                            _channel.value = it.data
                        }
                        else -> mutableChannelResult.value = false
                    }
                }
        }
    }

    fun getSongArtistInfo(channelId: String) {
        viewModelScope.launch {
            channelUseCase.getSongArtistInfo(channelId)
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            mutableChannelResult.value = true
                            convertToSongList(
                                it.data,
                                mutableListOf(),
                                SongViewType.SONG_TYPE_1
                            ) { convertedList ->
                                _songArtistInfoList.value = convertedList
                            }

                        }
                        else -> mutableChannelResult.value = false
                    }
                }
        }
    }

//    private fun searchTopHitVideo() {
//        viewModelScope.launch {
//            channelUseCase.searchHitVideoInfo()
//                .onStart {
//                    startLoading()
//                }.collect {
//                    stopLoading()
//                    when (it) {
//                        is Result.Success -> {
//                            mutableChannelResult.value = true
//                            _topHitSong.value = convertToSongList(
//                                it.data,
//                                convertTopHitList,
//                                SongViewType.SONG_TYPE_3
//                            )
//                        }
//                        else -> mutableChannelResult.value = false
//                    }
//                }
//        }
//    }

    fun getAlbumArtistInfo(channelId: String) {
        viewModelScope.launch {
            channelUseCase.getAlbumArtistInfo(channelId)
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            mutableChannelResult.value = true
                            _albumArtistInfoList.value = convertToAlbumList(it.data)
                        }
                        else -> mutableChannelResult.value = false
                    }
                }
        }
    }

    fun getAlbumFragment() {
        viewModelScope.launch {
            channelUseCase.getAlbumFragment()
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            mutableChannelResult.value = true
                            _albumFragment.value = convertToAlbumList(it.data)
                        }
                        else -> mutableChannelResult.value = false
                    }
                }
        }
    }

    fun getPodcastFragment() {
        viewModelScope.launch {
            channelUseCase.getPodcastFragment()
                .onStart {
                    startLoading()
                }.collect {
                    stopLoading()
                    when (it) {
                        is Result.Success -> {
                            mutableChannelResult.value = true
                            convertToSongList(
                                it.data,
                                mutableListOf(),
                                SongViewType.SONG_TYPE_1
                            ) { convertedList ->
                                _podcastFragment.value = convertedList
                            }
                        }
                        else -> mutableChannelResult.value = false
                    }
                }
        }
    }


    private fun deleteCollection(collectionPath: String): Task<Void> {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection(collectionPath)

        return collectionRef.get()
            .onSuccessTask { querySnapshot ->
                val batch = db.batch()
                for (document in querySnapshot) {
                    batch.delete(document.reference)
                }
                batch.commit()
            }
    }

    private fun convertToArtistList(searchChannelResponseDomain: SearchChannelResponseDomain): List<Artist> {
        searchChannelResponseDomain.items?.forEach { item ->
            val snippet = item.snippet
            if (snippet != null) {
                val image = snippet.thumbnails?.medium?.url.toString()
                val name = snippet.title.toString()
                val description = snippet.description.toString()
                val channelId = snippet.channelId.toString()

                convertArtistList.add(Artist(image, name, description, channelId))
            }
        }
        return convertArtistList
    }

    private fun convertToSongList(
        searchChannelResponseDomain: SearchChannelResponseDomain,
        list: MutableList<Song>,
        viewType: Int,
        callback: (List<Song>) -> Unit
    ) {
        searchChannelResponseDomain.items?.forEach { item ->
            val snippet = item.snippet
            if (snippet != null) {
                val image = snippet.thumbnails?.medium?.url.toString()
                val name = snippet.title.toString()
                val description = snippet.description.toString()
                getVideoUrl(item.id?.videoId.toString()) { url ->
                    list.add(Song(viewType, image, name, description, url))
                    callback(list)
                }
            }
        }
    }

    private fun convertToAlbumList(
        searchChannelResponseDomain: SearchChannelResponseDomain
    ): List<Album> {
        searchChannelResponseDomain.items?.forEach { item ->
            val snippet = item.snippet
            if (snippet != null) {
                val image = snippet.thumbnails?.medium?.url.toString()
                val name = snippet.title.toString()
                val publishedAt = snippet.publishedAt.toString()
                val year = publishedAt.substring(0, 4)
                val channelId = snippet.channelId.toString()
                val title = snippet.title.toString()

                convertAlbumArtistInfoList.add(Album(image, name, year, channelId, title))
            }
        }
        return convertAlbumArtistInfoList
    }

    private fun saveDataToFirestore(data: SearchChannelResponseDomain) {
        val artistsCollection = db.collection("SearchChannel")

        data.items?.forEach { item ->
            val snippet = item.snippet
            if (snippet != null) {
                val artistData = hashMapOf(
                    "title" to snippet.title,
                    "description" to snippet.description,
                    "customUrl" to snippet.customUrl,
                    "thumbnails" to snippet.thumbnails?.medium?.url,
                    "publishedAt" to snippet.publishedAt,
                    "country" to snippet.country,
                    "channelId" to snippet.channelId
                )

                artistsCollection.add(artistData)

            }
        }
    }

    private fun loadArtistsFromFirestore() {
        val searchChannelCollection = db.collection("SearchChannel")
        searchChannelCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val imageArtist = document.data["thumbnails"].toString()
                    val nameArtist = document.data["title"].toString()
                    val descriptionArtist = document.data["description"].toString()
                    val channelId = document.data["channelId"].toString()

                    val artist = Artist(imageArtist, nameArtist, descriptionArtist, channelId)
                    _artistsFromFirestore.add(artist)
                }
            }
            .addOnFailureListener {

            }
    }

    private suspend fun getDocumentCountInCollection(collectionName: String): Int {
        val artistsCollection = db.collection(collectionName)
        val documentCount: Int
        val querySnapshot = artistsCollection.get().await()

        documentCount = querySnapshot.size()
        return documentCount
    }

    private fun getVideoUrl(videoId: String, callback: (String) -> Unit) {
        val yt = YTExtractor(con = context, CACHING = true, LOGGING = true, retryCount = 3)

        CoroutineScope(Dispatchers.Main).launch {
            yt.extract(videoId)
            var url = ""
            var found = false

            if (yt.state == State.SUCCESS) {
                val ytFiles = yt.getYTFiles()
                ytFiles?.forEach { _, value ->
                    if (!found) {
                        url = value.url.toString()
                        found = true
                    }
                }
                callback(url)

            } else {
                callback("")
            }

        }

    }
}