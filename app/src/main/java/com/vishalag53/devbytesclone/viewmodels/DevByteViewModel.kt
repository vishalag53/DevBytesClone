package com.vishalag53.devbytesclone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vishalag53.devbytesclone.database.getDatabase
import com.vishalag53.devbytesclone.domain.Video
import com.vishalag53.devbytesclone.network.Network
import com.vishalag53.devbytesclone.network.asDomainModel
import com.vishalag53.devbytesclone.repository.VideosRepository
import kotlinx.coroutines.launch
import java.io.IOException

class DevByteViewModel(application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val videosRepository = VideosRepository(database)

    init {
        viewModelScope.launch {
            videosRepository.refreshVideos()
        }
    }

    val playlist = videosRepository.videos


    class Factory(private val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DevByteViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}