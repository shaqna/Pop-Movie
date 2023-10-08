package com.maou.popmovie.data.source

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.maou.popmovie.data.service.MovieUpdateWorker
import java.util.concurrent.TimeUnit

private const val REFRESH_RATE_MINUTES = 1L
private const val FETCH_LATEST_MOVIE_TASK = "FetchUpdateMovie"
private const val TAG_FETCH_LATEST_MOVIE = "FetchUpdateMovieTag"
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