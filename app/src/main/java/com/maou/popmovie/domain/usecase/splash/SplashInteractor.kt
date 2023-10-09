package com.maou.popmovie.domain.usecase.splash

import com.maou.popmovie.domain.repository.PopMovieRepository

class SplashInteractor(
    private val movieRepository: PopMovieRepository
): SplashUseCase {
    override fun isLocalDatabaseEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}