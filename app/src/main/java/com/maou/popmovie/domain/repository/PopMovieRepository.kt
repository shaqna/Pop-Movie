package com.maou.popmovie.domain.repository

import com.maou.popmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopMovieRepository {
    fun getPopMovie(apiKey: String): Flow<List<Movie>>
}