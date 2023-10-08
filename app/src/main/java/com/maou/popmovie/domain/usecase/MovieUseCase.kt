package com.maou.popmovie.domain.usecase

import com.maou.popmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(apiKey: String): Flow<Result<List<Movie>>>
}