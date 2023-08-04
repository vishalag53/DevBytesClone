package com.vishalag53.devbytesclone.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vishalag53.devbytesclone.database.getDatabase
import com.vishalag53.devbytesclone.repository.VideosRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params){
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        return try {
            repository.refreshVideos()
            Result.success()
        }catch (e: HttpException){
            Result.retry()
        }

    }

    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }

}