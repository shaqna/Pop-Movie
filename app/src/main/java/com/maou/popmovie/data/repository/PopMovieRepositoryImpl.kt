package com.maou.popmovie.data.repository

import com.maou.popmovie.data.source.local.LocalDataSource
import com.maou.popmovie.data.source.local.dao.MovieDao
import com.maou.popmovie.data.source.remote.service.ApiService
import com.maou.popmovie.domain.model.Movie
import com.maou.popmovie.domain.repository.PopMovieRepository
import kotlinx.coroutines.flow.Flow

class PopMovieRepositoryImpl(
    private val apiService: ApiService,
    private val localSource:  LocalDataSource

): PopMovieRepository {
    override fun getPopMovie(apiKey: String): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }
}