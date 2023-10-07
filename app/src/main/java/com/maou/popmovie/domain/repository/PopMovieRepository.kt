package com.maou.popmovie.domain.repository

import kotlinx.coroutines.flow.Flow

interface PopMovieRepository {
    fun getPopMovie(apiKey: String): Flow<List<Movie>>
}