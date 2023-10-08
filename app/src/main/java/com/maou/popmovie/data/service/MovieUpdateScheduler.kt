package com.maou.popmovie.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maou.popmovie.data.mapper.toListEntity
import com.maou.popmovie.data.source.local.LocalDataSource
import com.maou.popmovie.data.source.remote.service.ApiService
import org.koin.core.component.KoinComponent

class MovieUpdateScheduler(
    private val context: Context,
    private val workParams: WorkerParameters,
    private val apiService: ApiService,
    private val localSource: LocalDataSource
) : CoroutineWorker(context, workParams), KoinComponent {

    override suspend fun doWork(): Result {
        return try {
            val apiKey = inputData.getString("apiKey")
            val response = apiService.getMovies(apiKey!!)
            localSource.insertMovies(response.results.toListEntity())

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }
}