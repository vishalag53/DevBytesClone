package com.vishalag53.devbytesclone.domain

import com.vishalag53.devbytesclone.util.smartTruncate


data class Video(val title: String,
                val description: String,
                val url: String,
                val updated: String,
                val thumbnail: String){
    val shortDescription: String
        get() = description.smartTruncate(200)
}