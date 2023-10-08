package com.maou.popmovie.data.repository

import com.maou.popmovie.data.mapper.toListModel
import com.maou.popmovie.data.source.MovieDataSource
import com.maou.popmovie.data.source.local.LocalDataSource
import com.maou.popmovie.data.source.local.dao.MovieDao
import com.maou.popmovie.data.source.remote.service.ApiService
import com.maou.popmovie.domain.model.Movie
import com.maou.popmovie.domain.repository.PopMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class PopMovieRepositoryImpl(
    private val localSource:  LocalDataSource,
    private val movieDataSource: MovieDataSource
): PopMovieRepository {
    override fun getPopMovie(): Flow<Result<List<Movie>>> {
        return flow {
            try {
                val result = localSource.selectAllMovie()
                result.collect {listMovieEntity ->
                    emit(Result.success(listMovieEntity.toListModel()))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

        }.catch { e->
            emit(Result.failure(e))
        }
    }

    override fun fetchPopMoviePeriodically(apiKey: String) {
        movieDataSource.fetchMoviePeriodically(apiKey)
    }

    override fun cancelFetchPopMoviePeriodically() {
        movieDataSource.cancelFetchingMoviePeriodically()
    }
}