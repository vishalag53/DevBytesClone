package com.vishalag53.devbytesclone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vishalag53.devbytesclone.domain.Video
import com.vishalag53.devbytesclone.network.Network
import com.vishalag53.devbytesclone.network.asDomainModel
import kotlinx.coroutines.launch
import java.io.IOException

class DevByteViewModel(application: Application): AndroidViewModel(application) {
    private val _playlist = MutableLiveData<List<Video>>()
    val playlist: LiveData<List<Video>>
        get() = _playlist

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() = viewModelScope.launch {
            try {
                val playlist = Network.devbytes.getPlaylist().await()
                _playlist.postValue(playlist.asDomainModel())
            } catch (networkError: IOException) { }
    }


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