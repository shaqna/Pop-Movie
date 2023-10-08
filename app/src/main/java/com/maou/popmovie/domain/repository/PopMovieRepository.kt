package com.maou.popmovie.domain.repository

import com.maou.popmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopMovieRepository {
    fun getPopMovie(): Flow<Result<List<Movie>>>
    fun fetchPopMoviePeriodically(apiKey: String)

    fun cancelFetchPopMoviePeriodically()
}