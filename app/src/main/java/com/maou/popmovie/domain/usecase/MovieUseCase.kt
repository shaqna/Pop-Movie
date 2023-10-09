package com.maou.popmovie.domain.usecase

import com.maou.popmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(): Flow<Result<List<Movie>>>
    fun fetchPopMoviePeriodically(apiKey: String)
}