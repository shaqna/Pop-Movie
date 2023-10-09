package com.maou.popmovie.data.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maou.popmovie.data.mapper.toListEntity
import com.maou.popmovie.data.source.local.LocalDataSource
import com.maou.popmovie.data.source.remote.service.ApiService
import org.koin.core.component.KoinComponent

class MovieUpdateWorker(
    context: Context,
    workParams: WorkerParameters,
    private val apiService: ApiService,
    private val localSource: LocalDataSource
) : CoroutineWorker(context, workParams), KoinComponent {

    override suspend fun doWork(): Result {
        return try {
            val apiKey = inputData.getString("apiKey")
            val response = apiService.getMovies(apiKey!!)
            localSource.insertMovies(response.results.toListEntity())
            Log.d("My Worket TAG", "doWork: success")

            Result.success()
        } catch (e: Exception) {
            Log.d("My Worket TAG", "doWork: ${e.message.toString()}")
            Result.failure()
        }

    }
}