package com.maou.popmovie.data.source

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.maou.popmovie.data.service.MovieUpdateWorker
import com.maou.popmovie.utils.Constants.FETCH_LATEST_MOVIE_TASK
import com.maou.popmovie.utils.Constants.TAG_FETCH_LATEST_MOVIE
import java.util.concurrent.TimeUnit

private const val REFRESH_RATE_MINUTES = 1L
class MovieDataSource(
    private val workManager: WorkManager
) {
    fun fetchMoviePeriodically(apiKey: String) {
        val apiKeyData = Data.Builder().putString("apiKey", apiKey).build()

        val fetchMovieRequest = PeriodicWorkRequestBuilder<MovieUpdateWorker>(
            REFRESH_RATE_MINUTES, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).setInputData(apiKeyData).addTag(TAG_FETCH_LATEST_MOVIE)

        workManager.enqueueUniquePeriodicWork(
            FETCH_LATEST_MOVIE_TASK,
            ExistingPeriodicWorkPolicy.KEEP,
            fetchMovieRequest.build()
        )
    }

    fun cancelFetchingMoviePeriodically() {
        workManager.cancelAllWorkByTag(TAG_FETCH_LATEST_MOVIE)
    }
}