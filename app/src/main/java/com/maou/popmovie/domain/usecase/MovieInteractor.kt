package com.maou.popmovie.domain.usecase

import com.maou.popmovie.domain.model.Movie
import com.maou.popmovie.domain.repository.PopMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(
    private val repository: PopMovieRepository
): MovieUseCase {
    override fun getAllMovies(): Flow<Result<List<Movie>>> {
        return repository.getPopMovie()
    }
}