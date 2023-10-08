package com.maou.popmovie.data.source.local

import com.maou.popmovie.data.source.local.dao.MovieDao
import com.maou.popmovie.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val movieDao: MovieDao
) {
    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovieList(movies)

    fun selectAllMovie(): Flow<List<MovieEntity>> = movieDao.selectAllMovie()
}