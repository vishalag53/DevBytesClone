package com.vishalag53.devbytesclone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vishalag53.devbytesclone.database.VideosDatabase
import com.vishalag53.devbytesclone.database.asDomainModel
import com.vishalag53.devbytesclone.domain.Video
import com.vishalag53.devbytesclone.network.Network
import com.vishalag53.devbytesclone.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database: VideosDatabase){


    val videos: LiveData<List<Video>> = database.videoDao.getVideos().map {
        it.asDomainModel()
    }


    suspend fun refreshVideos(){
        withContext(Dispatchers.IO){
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}